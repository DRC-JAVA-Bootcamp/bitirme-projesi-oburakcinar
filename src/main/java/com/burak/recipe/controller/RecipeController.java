package com.burak.recipe.controller;

import com.burak.recipe.entity.Recipe;
import com.burak.recipe.exception.BadRequestException;
import com.burak.recipe.requestDto.recipe.RecipeSaveRequestDto;
import com.burak.recipe.responseDto.recipe.RecipeDeleteResponseDto;
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
import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Validated
public class RecipeController {

    private final RecipeService recipeService;

    private final CurrencyService currencyService;

    //currency parameter is optional
    @GetMapping("/allRecipes")
    public ResponseEntity<List<RecipeGetResponseDto>> findAllRecipes(@RequestParam(name = "currency", required = false) String currency) {

        List<RecipeGetResponseDto> recipeGetResponseDtoList = recipeService.findAll();

        //if currency parameter is given, convert the cost to desired currency
        if(currency != null) {
            double currencyRate = currencyService.findCurrencyRate(currency.toUpperCase());
            for (RecipeGetResponseDto dto : recipeGetResponseDtoList) {
                dto.setCost(dto.getCost() * currencyRate);
            }
        }
        return new ResponseEntity<>(recipeGetResponseDtoList, HttpStatus.OK);
    }

    @GetMapping("/recipesByCategory")
    public ResponseEntity<List<RecipeGetResponseDto>> findRecipesByCategory(@RequestParam("foodCategory") String foodCategory,
                                                     @RequestParam(name = "currency", required = false) String currency) {

        List<RecipeGetResponseDto> recipeGetResponseDtoList = recipeService.findRecipesByCategory(foodCategory.toUpperCase());

        //if currency parameter is given, convert the cost to desired currency
        if(currency != null) {
            double currencyRate = currencyService.findCurrencyRate(currency.toUpperCase());
            for (RecipeGetResponseDto dto : recipeGetResponseDtoList) {
                dto.setCost(dto.getCost() * currencyRate);
            }
        }
        return new ResponseEntity<>(recipeGetResponseDtoList, HttpStatus.OK);
    }

    @GetMapping("/recipesByMember")
    public ResponseEntity<List<RecipeGetResponseDto>> findRecipesByMemberId(@RequestParam("memberId") long memberId,
                                                                            @RequestParam(name = "currency", required = false) String currency) {

        List<RecipeGetResponseDto> recipeGetResponseDtoList = recipeService.findRecipesByMemberId(memberId);

        //if currency parameter is given, convert the cost to desired currency
        if(currency != null) {
            double currencyRate = currencyService.findCurrencyRate(currency.toUpperCase());
            for (RecipeGetResponseDto dto : recipeGetResponseDtoList) {
                dto.setCost(dto.getCost() * currencyRate);
            }
        }
        return new ResponseEntity<>(recipeGetResponseDtoList, HttpStatus.OK);
    }


    @GetMapping("/recipes")
    public ResponseEntity<RecipeGetResponseDto> findRecipeById(@RequestParam("recipeId") long recipeId,
                                                               @RequestParam(name = "currency", required = false) String currency) {

        RecipeGetResponseDto recipeGetResponseDto = recipeService.findRecipeGetResponseDtoById(recipeId);

        //if currency parameter is given, convert the cost to desired currency
        convertCurrencyIfCurrencyParameterExists(currency, recipeGetResponseDto);

        return new ResponseEntity<>(recipeGetResponseDto, HttpStatus.OK);
    }

    @GetMapping("/randomRecipe")
    public ResponseEntity<RecipeGetResponseDto> findRandomRecipe(@RequestParam(name = "currency", required = false) String currency) {

        RecipeGetResponseDto recipeGetResponseDto = recipeService.findRandomRecipe();

        convertCurrencyIfCurrencyParameterExists(currency, recipeGetResponseDto);

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

    @DeleteMapping("/recipes")
    public ResponseEntity<RecipeDeleteResponseDto> deleteRecipe(@RequestParam("recipeId") long recipeId) {
        RecipeDeleteResponseDto recipeDeleteResponseDto = recipeService.deleteById(recipeId);

        return new ResponseEntity<>(recipeDeleteResponseDto, HttpStatus.OK);
    }

    //if currency parameter is given, convert the cost to desired currency
    private void convertCurrencyIfCurrencyParameterExists(String currency, RecipeGetResponseDto recipeGetResponseDto) {
        if(currency != null) {
            double currencyRate = currencyService.findCurrencyRate(currency.toUpperCase());
            recipeGetResponseDto.setCost(recipeGetResponseDto.getCost() * currencyRate);
        }
    }


}
