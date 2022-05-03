package com.burak.recipe.service;

import com.burak.recipe.entity.Rate;
import com.burak.recipe.entity.Recipe;
import com.burak.recipe.exception.BadRequestException;
import com.burak.recipe.repository.RateRepository;
import com.burak.recipe.repository.RecipeRepository;
import com.burak.recipe.requestDto.rate.RateSaveRequestDto;
import com.burak.recipe.responseDto.rate.RateResponseDto;
import com.burak.recipe.util.converter.RateConverter;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RateServiceImpl implements RateService {

    private final RateRepository rateRepository;

    private final RecipeRepository recipeRepository;

    private final RateConverter rateConverter;

    private final ModelMapper modelMapper;

    @Override
    public RateResponseDto save(RateSaveRequestDto rateSaveRequestDto) {
        Rate rate = rateConverter.convertRateSaveRequestDtoToRate(rateSaveRequestDto);

        //checking for rating duplicate
        if(rateRepository.findByMemberAndRecipe(rate.getMember(), rate.getRecipe()) != null) {
            throw new BadRequestException("The recipe has already been rated!");
        }

        rateRepository.save(rate);

        updateRecipeRating(rate.getRecipe());

        RateResponseDto rateResponseDto = modelMapper.map(rateSaveRequestDto, RateResponseDto.class);
        rateResponseDto.setId(rate.getId());
        rateResponseDto.setMemberId(rate.getMember().getId());
        rateResponseDto.setRating(rate.getRating());

        return rateResponseDto;
    }

    private void updateRecipeRating(Recipe recipe) {
        List<Rate> rateList = rateRepository.findAllByRecipe(recipe);
        long sum = 0;
        for (Rate rate : rateList) {
            sum += rate.getRating();
        }
        double averageRating = (double) sum / rateList.size();
        recipe.setRating(averageRating);
        recipeRepository.save(recipe);
    }
}
