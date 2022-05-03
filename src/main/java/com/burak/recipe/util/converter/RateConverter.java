package com.burak.recipe.util.converter;

import com.burak.recipe.entity.Member;
import com.burak.recipe.entity.Rate;
import com.burak.recipe.entity.Recipe;
import com.burak.recipe.repository.MemberRepository;
import com.burak.recipe.requestDto.rate.RateSaveRequestDto;
import com.burak.recipe.service.RecipeService;
import com.burak.recipe.util.AuthenticationInfoUtil;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@Data
@RequiredArgsConstructor
public class RateConverter {

    private final AuthenticationInfoUtil authenticationInfoUtil;

    private final MemberRepository memberRepository;

    private final RecipeService recipeService;


    public Rate convertRateSaveRequestDtoToRate(RateSaveRequestDto rateSaveRequestDto) {
        //create empty like object
        Rate rate = new Rate();

        //get member
        String userName = authenticationInfoUtil.getAuthenticationUserName();
        Member member = memberRepository.findByUserName(userName);

        //get recipe
        long recipeId = rateSaveRequestDto.getRecipeId();
        Recipe recipe = recipeService.findById(recipeId);

        //get rating
        int rating = rateSaveRequestDto.getRating();

        //set all fields
        rate.setMember(member);
        rate.setRecipe(recipe);
        rate.setRating(rating);

        return rate;
    }
}
