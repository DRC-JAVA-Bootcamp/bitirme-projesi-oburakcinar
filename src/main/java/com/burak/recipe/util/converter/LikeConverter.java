package com.burak.recipe.util.converter;

import com.burak.recipe.entity.Comment;
import com.burak.recipe.entity.Like;
import com.burak.recipe.entity.Member;
import com.burak.recipe.entity.Recipe;
import com.burak.recipe.repository.MemberRepository;
import com.burak.recipe.requestDto.like.LikeRequestDto;
import com.burak.recipe.responseDto.comment.CommentResponseDto;
import com.burak.recipe.responseDto.like.LikeResponseDto;
import com.burak.recipe.service.RecipeService;
import com.burak.recipe.util.AuthenticationInfoUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class LikeConverter {

    private final AuthenticationInfoUtil authenticationInfoUtil;

    private final MemberRepository memberRepository;

    private final RecipeService recipeService;


    public Like convertLikeSaveRequestDtoToLike(LikeRequestDto likeSaveRequestDto) {
        //create empty like object
        Like like = new Like();

        //get member
        String userName = authenticationInfoUtil.getAuthenticationUserName();
        Member member = memberRepository.findByUserName(userName);

        //get recipe
        long recipeId = likeSaveRequestDto.getRecipeId();
        Recipe recipe = recipeService.findById(recipeId);

        //set all fields
        like.setMember(member);
        like.setRecipe(recipe);

        return like;
    }

    public LikeResponseDto convertFromLikeToLikeResponseDto(Like like) {
        LikeResponseDto likeResponseDto = new LikeResponseDto();

        likeResponseDto.setId(like.getId());
        likeResponseDto.setMemberId(like.getMember().getId());
        likeResponseDto.setRecipeId(like.getRecipe().getId());

        return likeResponseDto;
    }
}
