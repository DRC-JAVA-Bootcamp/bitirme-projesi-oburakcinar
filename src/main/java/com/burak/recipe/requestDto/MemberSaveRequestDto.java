package com.burak.recipe.requestDto;

import com.burak.recipe.entity.Address;
import lombok.Data;

import java.time.LocalDate;

@Data
public class MemberSaveRequestDto {

    private String name;

    private String lastName;

    private String userName;

    private String password;

    private LocalDate birthDate;

    private Address address;
}
