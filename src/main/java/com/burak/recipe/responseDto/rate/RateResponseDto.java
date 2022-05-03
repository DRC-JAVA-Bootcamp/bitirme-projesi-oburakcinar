package com.burak.recipe.responseDto.rate;

import lombok.Data;

@Data
public class RateResponseDto {

    private Long id;

    private long memberId;

    private long recipeId;

    private int rating;
}
