package com.vht.client.validation.enumvalues.impl;

import com.vht.client.definition.message.exception.ExceptionMessages;
import com.vht.client.validation.enumvalues.ValidEnum;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

public class EnumConstraintValidator implements ConstraintValidator<ValidEnum, Enum<?>> {

    private Pattern pattern;

    @Override
    public void initialize(ValidEnum constraintAnnotation) {
        try {
            this.pattern = Pattern.compile(constraintAnnotation.regexp());
        } catch (PatternSyntaxException e) {
            throw new IllegalArgumentException(String.format(ExceptionMessages.INVALID_REGEX, constraintAnnotation.regexp()));
        }
    }

    @Override
    public boolean isValid(Enum<?> anEnum, ConstraintValidatorContext constraintValidatorContext) {
        if (Objects.isNull(anEnum)) {
            return true;
        }
        Matcher matcher = pattern.matcher(anEnum.name());
        return matcher.matches();
    }
}
