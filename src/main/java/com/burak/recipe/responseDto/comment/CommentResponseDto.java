package com.burak.recipe.responseDto.comment;

import com.burak.recipe.entity.Member;
import com.burak.recipe.entity.Recipe;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Data
public class CommentResponseDto {

    private Long id;

    private long memberId;

    private long recipeId;

    private String text;
}
