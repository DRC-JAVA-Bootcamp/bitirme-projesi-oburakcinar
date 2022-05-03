package com.burak.recipe.service;

import com.burak.recipe.entity.FoodCategoryEnum;
import com.burak.recipe.entity.Member;
import com.burak.recipe.entity.Recipe;
import com.burak.recipe.exception.BadRequestException;
import com.burak.recipe.exception.EntityNotFoundException;
import com.burak.recipe.repository.MemberRepository;
import com.burak.recipe.repository.RecipeRepository;
import com.burak.recipe.requestDto.recipe.RecipeSaveRequestDto;
import com.burak.recipe.responseDto.recipe.RecipeDeleteResponseDto;
import com.burak.recipe.responseDto.recipe.RecipeGetResponseDto;
import com.burak.recipe.util.AuthenticationInfoUtil;
import com.burak.recipe.util.converter.RecipeConverter;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class RecipeServiceImpl implements RecipeService{

    private final RecipeRepository recipeRepository;

    private final MemberService memberService;

    private final MemberRepository memberRepository;

    private final ModelMapper modelMapper;

    private final AuthenticationInfoUtil authInfoUtil;

    private final RecipeConverter recipeConverter;


    @Override
    public Recipe save(RecipeSaveRequestDto recipeSaveRequestDto) {
        //food type string must be uppercase to convert it into enum
        recipeSaveRequestDto.setFoodCategory(recipeSaveRequestDto.getFoodCategory().toUpperCase());

        Recipe recipe = modelMapper.map(recipeSaveRequestDto, Recipe.class);

        //post date should be now
        recipe.setPostDate(LocalDateTime.now());

        //finding the user who currently logged in
        String memberUserName = authInfoUtil.getAuthenticationUserName();
        Member member = memberRepository.findByUserName(memberUserName);
        recipe.setMember(member);

        recipeRepository.save(recipe);

        return recipe;
    }

    @Override
    public Recipe findById(long recipeId) {
        Optional<Recipe> recipeOpt = recipeRepository.findById(recipeId);
        if (recipeOpt.isPresent()) {
            Recipe recipe = recipeOpt.get();
            return recipe;
        } else {
            //if member object is null, throw exception
            throw new EntityNotFoundException("Recipe id not found - " + recipeId);
        }
    }

    @Override
    public RecipeGetResponseDto findRecipeGetResponseDtoById(long recipeId) {
        Recipe recipe = findById(recipeId);
        RecipeGetResponseDto recipeGetResponseDto = modelMapper.map(recipe, RecipeGetResponseDto.class);
        recipeGetResponseDto.setMemberId(recipe.getMember().getId());

        return recipeGetResponseDto;
    }

    @Override
    public RecipeGetResponseDto findRandomRecipe() {
        //finding a random recipe
        List<Recipe> recipes = recipeRepository.findAll();
        Random random = new Random();
        long randomIndex = random.nextInt(recipes.size()) + 1; //recipe id start at index 1
        Recipe randomRecipe = recipeRepository.findById(randomIndex).get();

        RecipeGetResponseDto recipeGetResponseDto = modelMapper.map(randomRecipe, RecipeGetResponseDto.class);

        return recipeGetResponseDto;
    }

    @Override
    public List<RecipeGetResponseDto> findAll() {
        List<Recipe> recipes = recipeRepository.findAll();

        return recipeConverter.convertFromRecipeListToGetResponseDtoList(recipes);
    }

    @Override
    public List<RecipeGetResponseDto> findRecipesByCategory(String foodCategory) {
        try {
            FoodCategoryEnum category = FoodCategoryEnum.valueOf(foodCategory);
            List<Recipe> recipes = recipeRepository.findAllByFoodCategory(category);
            return recipeConverter.convertFromRecipeListToGetResponseDtoList(recipes);
        } catch (Exception e) {
            throw new BadRequestException("Invalid food category");
        }
    }

    @Override
    public List<RecipeGetResponseDto> findRecipesByMemberId(long memberId) {
        Member member = memberService.findById(memberId);
        List<Recipe> recipes = recipeRepository.findAllByMember(member);

        return recipeConverter.convertFromRecipeListToGetResponseDtoList(recipes);
    }

    @Override
    public RecipeDeleteResponseDto deleteById(long recipeId) {
        String userName = authInfoUtil.getAuthenticationUserName();
        Member member = memberRepository.findByUserName(userName);

        Recipe recipe = findById(recipeId);
        List<Recipe> memberRecipes = recipeRepository.findAllByMember(member);

        if(memberRecipes.contains(recipe)) {
            recipeRepository.delete(recipe);
            return modelMapper.map(recipe, RecipeDeleteResponseDto.class);
        } else {
            throw new BadRequestException("This member have not published a recipe with the id " + recipeId);
        }
    }


}
