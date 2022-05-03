package com.burak.recipe.validation;

import com.burak.recipe.entity.FoodTypeEnum;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class FoodTypeValidator implements ConstraintValidator<FoodType, String> {

    //String foodType is the object to be validated
    @Override
    public boolean isValid(String foodType, ConstraintValidatorContext context) {
        foodType = foodType.toUpperCase();
        try {
            if (FoodTypeEnum.valueOf(foodType) instanceof FoodTypeEnum) {
                return true; //means object is validated
            }
            return false;  //means object is not validated
        } catch (Exception e) {
            return false;
        }

    }
}
