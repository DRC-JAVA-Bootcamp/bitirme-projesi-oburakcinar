package com.burak.recipe.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = FoodTypeValidator.class)
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface FoodType {
    public String value() default "";

    //define default error message
    public String message() default "Invalid food type";

    //define default groups, ---> groups can group related constraints
    public Class<?>[] groups() default {};

    //define default payloads, ---> payloads provide custom details about validation failure
    public Class<? extends Payload>[] payload() default {};
}
