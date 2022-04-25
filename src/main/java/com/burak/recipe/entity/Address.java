package com.burak.recipe.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table
@Data
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column
    private String country;

    @Column
    private String city;

    @Column
    private String street;

    @Column(name = "zip_code")
    private Long zipCode;

    @OneToOne(mappedBy="address",
            cascade= {CascadeType.DETACH, CascadeType.MERGE,
                    CascadeType.PERSIST, CascadeType.REFRESH})
    private Member member;
}
