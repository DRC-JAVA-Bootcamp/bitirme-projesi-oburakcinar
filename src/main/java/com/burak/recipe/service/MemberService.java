package com.burak.recipe.service;

import com.burak.recipe.entity.Member;
import com.burak.recipe.requestDto.AddressSaveRequestDto;
import com.burak.recipe.requestDto.MemberSaveRequestDto;
import com.burak.recipe.responseDto.MemberDeleteResponseDto;
import com.burak.recipe.responseDto.MemberSaveResponseDto;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface MemberService extends UserDetailsService {
    MemberSaveResponseDto save(MemberSaveRequestDto memberSaveRequestDto);

    Member findById(long memberId);

    MemberDeleteResponseDto deleteById(long memberId);

    MemberSaveResponseDto updateMemberAddress(AddressSaveRequestDto addressSaveRequestDto);

    List<Member> findAll();
}
