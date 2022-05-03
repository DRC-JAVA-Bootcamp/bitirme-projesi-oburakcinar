package com.burak.recipe.requestDto.like;

import lombok.Data;

import javax.validation.constraints.Positive;

@Data
public class LikeRequestDto {

    @Positive(message = "Recipe id must be positive")
    private long recipeId;
}
