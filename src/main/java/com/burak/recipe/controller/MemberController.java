package com.burak.recipe.controller;

import com.burak.recipe.entity.Member;
import com.burak.recipe.exception.BadRequestException;
import com.burak.recipe.requestDto.AddressSaveRequestDto;
import com.burak.recipe.requestDto.MemberSaveRequestDto;
import com.burak.recipe.responseDto.MemberDeleteResponseDto;
import com.burak.recipe.responseDto.MemberSaveResponseDto;
import com.burak.recipe.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;


@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Validated
public class MemberController {

    private final MemberService memberService;


    @GetMapping("/member")
    public ResponseEntity<Member> findMemberById(@RequestParam("memberId") long memberId) {
        Member member = memberService.findById(memberId);

        return new ResponseEntity<>(member, HttpStatus.OK);
    }

    @GetMapping("members")
    public ResponseEntity<List<Member>> findAllMembers() {
        List<Member> members = memberService.findAll();

        return new ResponseEntity<>(members, HttpStatus.OK);
    }

    @PostMapping("/member")
    public ResponseEntity<MemberSaveResponseDto> saveMember(@Valid @RequestBody MemberSaveRequestDto memberSaveRequestDto,
                                                            BindingResult theBindingResult) {
        //validate memberSaveRequestDto
        if(theBindingResult.hasErrors()) {
            throw new BadRequestException(theBindingResult.getFieldError().getDefaultMessage());
        }

        //the address of the member is also created here
        MemberSaveResponseDto memberSaveResponseDto = memberService.save(memberSaveRequestDto);

        return new ResponseEntity<>(memberSaveResponseDto, HttpStatus.OK);
    }

    @PutMapping("member")
    public ResponseEntity<MemberSaveResponseDto> updateMemberAddress(@Valid @RequestBody AddressSaveRequestDto addressSaveRequestDto,
                                                                     BindingResult theBindingResult) {
        if(theBindingResult.hasErrors()) {
            throw new BadRequestException(theBindingResult.getFieldError().getDefaultMessage());
        }
        MemberSaveResponseDto memberSaveResponseDto = memberService.updateMemberAddress(addressSaveRequestDto);

        return new ResponseEntity<>(memberSaveResponseDto, HttpStatus.OK);
    }

    @DeleteMapping("/member")
    public ResponseEntity<MemberDeleteResponseDto> deleteMemberById(@RequestParam("memberId") long memberId) {
        //only admin has authorization to delete a member according to securityConfig class
        MemberDeleteResponseDto memberDeleteResponseDto = memberService.deleteById(memberId);

        return new ResponseEntity<>(memberDeleteResponseDto, HttpStatus.OK);
    }



}
