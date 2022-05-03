package com.burak.recipe.requestDto.rate;

import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Positive;

@Data
public class RateSaveRequestDto {

    @Positive(message = "Recipe id must be positive")
    private long recipeId;

    @Min(value = 1, message = "rating must be equal or greater than 1")
    @Max(value = 5, message = "rating must be equal or less than 5")
    private int rating;
}
