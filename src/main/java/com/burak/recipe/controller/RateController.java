package com.burak.recipe.controller;

import com.burak.recipe.exception.BadRequestException;
import com.burak.recipe.requestDto.rate.RateSaveRequestDto;
import com.burak.recipe.responseDto.rate.RateResponseDto;
import com.burak.recipe.service.RateService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Validated
public class RateController {

    private final RateService rateService;

    @PostMapping("/rates")
    public ResponseEntity<RateResponseDto> rateRecipe(@Valid @RequestBody RateSaveRequestDto rateSaveRequestDto,
                                                      BindingResult bindingResult) {
        if(bindingResult.hasErrors()) {
            throw new BadRequestException(bindingResult.getFieldError().getDefaultMessage());
        }

        RateResponseDto rateResponseDto = rateService.save(rateSaveRequestDto);

        return new ResponseEntity<>(rateResponseDto, HttpStatus.OK);
    }
}
