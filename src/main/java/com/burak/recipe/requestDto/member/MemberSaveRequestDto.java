package com.burak.recipe.requestDto.member;

import com.burak.recipe.entity.Address;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import java.time.LocalDate;

@Data
public class MemberSaveRequestDto {

    @NotEmpty(message = "name field cannot be empty")
    private String name;

    @NotEmpty(message = "lastname field cannot be empty")
    private String lastName;

    @NotEmpty(message = "userName field cannot be empty")
    private String userName;

    @NotEmpty(message = "password field cannot be empty")
    private String password;

    @NotNull
    @Past(message = "birth date must be a past date")
    private LocalDate birthDate;

    @Valid
    @NotNull(message = "address field cannot be empty")
    private Address address;
}
