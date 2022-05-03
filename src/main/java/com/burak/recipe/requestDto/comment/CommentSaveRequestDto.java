package com.burak.recipe.requestDto.comment;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Positive;

@Data
public class CommentSaveRequestDto {

    @Positive(message = "Recipe id must be positive")
    private long recipeId;

    @NotEmpty(message = "Text field cannot be empty")
    private String text;
}
