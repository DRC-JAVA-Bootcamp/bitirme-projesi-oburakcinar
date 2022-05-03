package com.burak.recipe.controller;

import com.burak.recipe.entity.Recipe;
import com.burak.recipe.exception.BadRequestException;
import com.burak.recipe.requestDto.recipe.RecipeSaveRequestDto;
import com.burak.recipe.responseDto.recipe.RecipeGetResponseDto;
import com.burak.recipe.service.CurrencyService;
import com.burak.recipe.service.RecipeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Validated
public class RecipeController {

    private final RecipeService recipeService;

    private final CurrencyService currencyService;

    //currency parameter is optional
    @GetMapping("/recipes")
    public ResponseEntity<RecipeGetResponseDto> findRecipeById(@RequestParam("recipeId") long recipeId,
                                                               @RequestParam(name = "currency", required = false) String currency) {

        RecipeGetResponseDto recipeGetResponseDto = recipeService.findById(recipeId);

        //if currency parameter is given, convert the cost to desired currency
        if(currency != null) {
            double currencyRate = currencyService.findCurrencyRate(currency.toUpperCase());
            recipeGetResponseDto.setCost(recipeGetResponseDto.getCost() * currencyRate);
        }

        return new ResponseEntity<>(recipeGetResponseDto, HttpStatus.OK);
    }

    @PostMapping("/recipes")
    public ResponseEntity<Recipe> saveRecipe(@Valid @RequestBody RecipeSaveRequestDto recipeSaveRequestDto, BindingResult bindingResult) {
        //validate recipeSaveRequestDto
        if(bindingResult.hasErrors()) {
            throw new BadRequestException(bindingResult.getFieldError().getDefaultMessage());
        }
        Recipe recipe = recipeService.save(recipeSaveRequestDto);

        return new ResponseEntity<>(recipe, HttpStatus.OK);
    }


}
