package com.lny.nearby.validator;

import com.lny.nearby.document.PlaceRequestDocument;
import com.lny.nearby.service.PlaceService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

/**
 * Validator for all place request in Place API
 */
public class PlaceRequestValidator implements Validator {

    private static final Logger logger = LoggerFactory.getLogger(PlaceRequestValidator.class);

    @Override
    public boolean supports(Class<?> aClass) {
        return PlaceRequestDocument.class.isAssignableFrom(aClass);
    }

    @Override
    public void validate(Object target, Errors errors) {

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "latitude", "field.required");

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "longitude", "field.required");

        PlaceRequestDocument requestDocument = (PlaceRequestDocument) target;

        if (!StringUtils.isEmpty(requestDocument.getRankedBy())) {
            if (requestDocument.getRankedBy().equalsIgnoreCase("distance")) {
                if (StringUtils.isEmpty(requestDocument.getKeyword()))
                    errors.rejectValue(
                            "code",
                            "request.invalid",
                            "If distance is specified in rankedBy, the keyword is required");
            }
        }
    }
}
