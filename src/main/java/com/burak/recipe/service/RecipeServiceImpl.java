package com.burak.recipe.service;

import com.burak.recipe.entity.Member;
import com.burak.recipe.entity.Recipe;
import com.burak.recipe.exception.EntityNotFoundException;
import com.burak.recipe.repository.MemberRepository;
import com.burak.recipe.repository.RecipeRepository;
import com.burak.recipe.requestDto.recipe.RecipeSaveRequestDto;
import com.burak.recipe.responseDto.recipe.RecipeGetResponseDto;
import com.burak.recipe.util.AuthenticationInfoUtil;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RecipeServiceImpl implements RecipeService{

    private final RecipeRepository recipeRepository;

    private final MemberRepository memberRepository;

    private final ModelMapper modelMapper;

    private final AuthenticationInfoUtil authInfoUtil;

    @Override
    public Recipe save(RecipeSaveRequestDto recipeSaveRequestDto) {
        //food type string must be uppercase to convert it into enum
        recipeSaveRequestDto.setFoodType(recipeSaveRequestDto.getFoodType().toUpperCase());

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
    public RecipeGetResponseDto findById(long recipeId) {
        Optional<Recipe> recipeOpt = recipeRepository.findById(recipeId);
        if (recipeOpt.isPresent()) {
            Recipe recipe = recipeOpt.get();
            RecipeGetResponseDto recipeGetResponseDto = modelMapper.map(recipe, RecipeGetResponseDto.class);
            recipeGetResponseDto.setMemberId(recipe.getMember().getId());
            return recipeGetResponseDto;
        } else {
            //if member object is null, throw exception
            throw new EntityNotFoundException("Member id not found - " + recipeId);
        }
    }

}
