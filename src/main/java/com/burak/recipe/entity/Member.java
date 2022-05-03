package com.burak.recipe.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "member")
@Data
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;

    @Column
    private String name;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "user_name")
    private String userName;

    @Column
    private String password;

    @Column(name = "birth_date")
    private LocalDate birthDate;

    @OneToOne(fetch=FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name="member_address_id")
    private Address address;

    @ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name="role_id")
    private Role role;


}
