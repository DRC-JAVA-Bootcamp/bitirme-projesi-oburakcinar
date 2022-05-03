package com.burak.recipe.validation;

import com.burak.recipe.entity.FoodCategoryEnum;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class FoodCategoryValidator implements ConstraintValidator<FoodCategory, String> {

    //String foodType is the object to be validated
    @Override
    public boolean isValid(String foodCategory, ConstraintValidatorContext context) {
        foodCategory = foodCategory.toUpperCase();
        try {
            if (FoodCategoryEnum.valueOf(foodCategory) instanceof FoodCategoryEnum) {
                return true; //means object is validated
            }
            return false;  //means object is not validated
        } catch (Exception e) {
            return false;
        }

    }
}
