package com.burak.recipe.entity;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "food_type")
@Data
public abstract class Recipe {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;

    @Column
    private String name;

    @Column
    private LocalDateTime postDate;

    @Column
    private int prepDuration;

    @Column
    @ElementCollection
    private List<String> ingredients;

    @Column
    @ElementCollection
    private List<String> prepSteps;

    @Column
    private double cost;

    //private List<Comment> comments;

    //private List<Like> likes;

    @Column
    private double rating;


    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;
}
