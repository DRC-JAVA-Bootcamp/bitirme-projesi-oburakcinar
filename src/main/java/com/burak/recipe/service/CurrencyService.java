package com.burak.recipe.service;

import com.burak.recipe.responseDto.currency.CurrencyGetRateResponseDto;
import org.springframework.http.ResponseEntity;

public interface CurrencyService {
    Double findCurrencyRate(String currency);
}
