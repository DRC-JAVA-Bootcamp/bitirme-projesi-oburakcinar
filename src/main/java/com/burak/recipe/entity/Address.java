package com.burak.recipe.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Positive;

@Entity
@Table
@Data
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column
    @NotEmpty(message = "country field cannot be empty")
    private String country;

    @Column
    @NotEmpty(message = "city field cannot be empty")
    private String city;

    @Column
    @NotEmpty(message = "street field cannot be empty")
    private String street;

    @Column(name = "zip_code")
    @Positive(message = "zip code must be positive")
    private Long zipCode;

    @JsonIgnore
    @OneToOne(mappedBy="address",
            cascade= {CascadeType.DETACH, CascadeType.MERGE,
                    CascadeType.PERSIST, CascadeType.REFRESH})
    private Member member;
}
