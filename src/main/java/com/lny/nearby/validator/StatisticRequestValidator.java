package com.lny.nearby.validator;

import com.lny.nearby.document.StatisticRequestDocument;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

/**
 * Validator for all statistic request in Place API
 */
public class StatisticRequestValidator implements Validator {
    @Override
    public boolean supports(Class<?> aClass) {
        return StatisticRequestDocument.class.isAssignableFrom(aClass);
    }

    @Override
    public void validate(Object target, Errors errors) {
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "latitude", "field.required");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "longitude", "field.required");
    }
}
