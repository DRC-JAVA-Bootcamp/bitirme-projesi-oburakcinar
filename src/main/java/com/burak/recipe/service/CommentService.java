package com.burak.recipe.service;

import com.burak.recipe.entity.Comment;
import com.burak.recipe.requestDto.comment.CommentSaveRequestDto;
import com.burak.recipe.responseDto.comment.CommentResponseDto;

import java.util.List;

public interface CommentService {
    CommentResponseDto save(CommentSaveRequestDto commentSaveRequestDto);

    CommentResponseDto deleteByMemberIdAndRecipeId(long memberId, long recipeId);

    Comment findByMemberIdAndRecipeId(long memberId, long recipeId);

    CommentResponseDto findCommentResponseDtoByMemberIdAndRecipeId(long memberId, long recipeId);

    List<CommentResponseDto> findAllByRecipeId(long recipeId);

    List<CommentResponseDto> findAllByMemberId(long memberId);
}
