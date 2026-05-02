package com.substring.irctc.annotations;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class CoachValidator implements ConstraintValidator<ValidCoach, Integer> {
    @Override
    public boolean isValid(Integer s, ConstraintValidatorContext constraintValidatorContext) {
        if(s > 100) {
            return true;
        }
        return false;
    }
}
