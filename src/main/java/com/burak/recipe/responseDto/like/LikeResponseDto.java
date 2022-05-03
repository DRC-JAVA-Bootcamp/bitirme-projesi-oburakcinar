package com.burak.recipe.responseDto.like;

import lombok.Data;

@Data
public class LikeResponseDto {

    private Long id;

    private long memberId;

    private long recipeId;

}
