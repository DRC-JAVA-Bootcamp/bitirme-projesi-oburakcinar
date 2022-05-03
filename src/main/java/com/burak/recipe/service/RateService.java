package com.burak.recipe.service;

import com.burak.recipe.requestDto.rate.RateSaveRequestDto;
import com.burak.recipe.responseDto.rate.RateResponseDto;

public interface RateService {
    RateResponseDto save(RateSaveRequestDto rateSaveRequestDto);
}
