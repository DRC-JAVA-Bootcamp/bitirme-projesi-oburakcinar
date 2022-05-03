package com.burak.recipe.requestDto.address;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Positive;


@Data
public class AddressSaveRequestDto {


    @NotEmpty(message = "country field cannot be empty")
    private String country;

    @NotEmpty(message = "city field cannot be empty")
    private String city;

    @NotEmpty(message = "street field cannot be empty")
    private String street;

    @Positive(message = "zipcode must be positive")
    private Long zipCode;
}
