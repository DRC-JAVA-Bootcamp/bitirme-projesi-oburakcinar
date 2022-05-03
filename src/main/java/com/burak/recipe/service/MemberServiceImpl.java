package com.burak.recipe.service;

import com.burak.recipe.entity.Address;
import com.burak.recipe.entity.Member;
import com.burak.recipe.entity.Role;
import com.burak.recipe.exception.BadRequestException;
import com.burak.recipe.exception.EntityAlreadyExistsException;
import com.burak.recipe.exception.EntityNotFoundException;
import com.burak.recipe.repository.AddressRepository;
import com.burak.recipe.repository.MemberRepository;
import com.burak.recipe.repository.RoleRepository;
import com.burak.recipe.requestDto.AddressSaveRequestDto;
import com.burak.recipe.requestDto.MemberSaveRequestDto;
import com.burak.recipe.responseDto.MemberDeleteResponseDto;
import com.burak.recipe.responseDto.MemberSaveResponseDto;
import com.burak.recipe.util.AuthenticationInfoUtil;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService{

    private final MemberRepository memberRepository;

    private final RoleRepository roleRepository;

    private final AddressRepository addressRepository;

    private final ModelMapper modelMapper;

    private final PasswordEncoder passwordEncoder;

    private final AuthenticationInfoUtil authenticationInfoUtil;

    @Override
    public List<Member> findAll() {
        return memberRepository.findAll();
    }

    @Override
    public Member findById(long memberId) {
        if(memberId < 0) {
            throw new BadRequestException("Member id cannot be negative");
        }
        Optional<Member> member = memberRepository.findById(memberId);
        if (member.isPresent()) {
            return member.get();
        } else {
            //if member object is null, throw exception
            throw new EntityNotFoundException("Member id not found - " + memberId);
        }
    }

    @Override
    public MemberSaveResponseDto save(MemberSaveRequestDto memberSaveRequestDto) {
        String newMemberUserName = memberSaveRequestDto.getUserName();

        //if username already exists, throw an exception
        if (memberRepository.findByUserName(newMemberUserName) != null) {
            throw new EntityAlreadyExistsException("Username " + newMemberUserName + " already exists!");
        }

        //converting dto to member class
        Member member = modelMapper.map(memberSaveRequestDto, Member.class);

        //setting member role
        member.setRole(roleRepository.findByName("ROLE_MEMBER"));

        //encrypting the password
        member.setPassword(passwordEncoder.encode(member.getPassword()));

        //saving the member
        memberRepository.save(member);

        //converting member to memberSaveResponseDto
        MemberSaveResponseDto responseDto = modelMapper.map(member, MemberSaveResponseDto.class);
        return responseDto;
    }

    @Override
    public MemberDeleteResponseDto deleteById(long memberId) {
        Member member = findById(memberId);
        memberRepository.deleteById(memberId);

        MemberDeleteResponseDto memberDeleteResponseDto = modelMapper.map(member, MemberDeleteResponseDto.class);

        return memberDeleteResponseDto;
    }

    @Override
    public MemberSaveResponseDto updateMemberAddress(AddressSaveRequestDto addressSaveRequestDto) {

        //getting username from basic authentication
        String memberUserName = authenticationInfoUtil.getAuthenticationUserName();

        //finding the number by user name
        Member member = memberRepository.findByUserName(memberUserName);

        //updating address
        Address address = member.getAddress();
        address.setCity(addressSaveRequestDto.getCity());
        address.setCountry(addressSaveRequestDto.getCountry());
        address.setStreet(addressSaveRequestDto.getStreet());
        address.setZipCode(addressSaveRequestDto.getZipCode());

        //saving address
        addressRepository.save(address);

        //creating response
        MemberSaveResponseDto responseDto = modelMapper.map(member, MemberSaveResponseDto.class);

        return responseDto;
    }




    @Override
    @Transactional
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        Member member = memberRepository.findByUserName(userName);
        if (member == null) {
            throw new UsernameNotFoundException("Invalid username or password.");
        }
        Collection<Role> roles = new ArrayList<>();
        roles.add(member.getRole());
        return new org.springframework.security.core.userdetails.User(member.getUserName(), member.getPassword(),
                mapRolesToAuthorities(roles));
    }

    private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Collection<Role> roles) {
        return roles.stream().map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList());
    }


}
