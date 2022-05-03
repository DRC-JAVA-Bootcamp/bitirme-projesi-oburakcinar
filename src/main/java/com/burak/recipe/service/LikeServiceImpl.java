package com.burak.recipe.service;

import com.burak.recipe.entity.Like;
import com.burak.recipe.entity.Member;
import com.burak.recipe.entity.Recipe;
import com.burak.recipe.exception.BadRequestException;
import com.burak.recipe.repository.LikeRepository;
import com.burak.recipe.repository.MemberRepository;
import com.burak.recipe.requestDto.like.LikeRequestDto;
import com.burak.recipe.responseDto.like.LikeResponseDto;
import com.burak.recipe.util.AuthenticationInfoUtil;
import com.burak.recipe.util.converter.LikeConverter;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class LikeServiceImpl implements LikeService{

    private final LikeRepository likeRepository;

    private final LikeConverter likeConverter;

    private final MemberService memberService;

    private final MemberRepository memberRepository;

    private final RecipeService recipeService;

    private final ModelMapper modelMapper;

    private final AuthenticationInfoUtil authenticationInfoUtil;

    @Override
    public LikeResponseDto save(LikeRequestDto likeSaveRequestDto) {
        Like like = likeConverter.convertLikeSaveRequestDtoToLike(likeSaveRequestDto);

        //checking for like duplicate
        if(likeRepository.findByMemberAndRecipe(like.getMember(), like.getRecipe()) != null) {
            throw new BadRequestException("The recipe has already been liked!");
        }

        likeRepository.save(like);

        LikeResponseDto likeResponseDto = modelMapper.map(likeSaveRequestDto, LikeResponseDto.class);
        likeResponseDto.setId(like.getId());
        likeResponseDto.setMemberId(like.getMember().getId());
        return likeResponseDto;
    }

    @Override
    public LikeResponseDto delete(long recipeId) {
        String userName = authenticationInfoUtil.getAuthenticationUserName();
        Member member = memberRepository.findByUserName(userName);
        Recipe recipe = recipeService.findById(recipeId);

        Like like = likeRepository.findByMemberAndRecipe(member, recipe);

        if(like == null) {
            throw new BadRequestException("Like does not exist");
        }

        likeRepository.delete(like);

        LikeResponseDto likeResponseDto = new LikeResponseDto();
        likeResponseDto.setId(like.getId());
        likeResponseDto.setRecipeId(recipeId);
        likeResponseDto.setMemberId(member.getId());

        return likeResponseDto;
    }

    @Override
    public List<LikeResponseDto> findAllByRecipeId(long recipeId) {
        Recipe recipe = recipeService.findById(recipeId);
        List<Like> likes = likeRepository.findAllByRecipe(recipe);

        List<LikeResponseDto> responseDtoList = new ArrayList<>();

        for (Like like : likes) {
            responseDtoList.add(likeConverter.convertFromLikeToLikeResponseDto(like));
        }

        return responseDtoList;
    }

    @Override
    public List<LikeResponseDto> findAllByMemberId(long memberId) {
        Member member = memberService.findById(memberId);
        List<Like> likes = likeRepository.findAllByMember(member);

        List<LikeResponseDto> responseDtoList = new ArrayList<>();

        for (Like like : likes) {
            responseDtoList.add(likeConverter.convertFromLikeToLikeResponseDto(like));
        }

        return responseDtoList;
    }
}
