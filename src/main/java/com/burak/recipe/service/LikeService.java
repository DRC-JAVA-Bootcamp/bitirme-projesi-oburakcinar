package com.burak.recipe.service;

import com.burak.recipe.requestDto.like.LikeRequestDto;
import com.burak.recipe.responseDto.like.LikeResponseDto;

import java.util.List;

public interface LikeService {
    LikeResponseDto save(LikeRequestDto likeSaveRequestDto);

    LikeResponseDto delete(long recipeId);

    List<LikeResponseDto> findAllByRecipeId(long recipeId);

    List<LikeResponseDto> findAllByMemberId(long memberId);
}
