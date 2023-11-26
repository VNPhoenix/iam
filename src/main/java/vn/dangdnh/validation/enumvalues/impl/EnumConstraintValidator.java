package vn.dangdnh.validation.enumvalues.impl;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import vn.dangdnh.validation.enumvalues.ValidEnum;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

public class EnumConstraintValidator implements ConstraintValidator<ValidEnum, Enum<?>> {

    private Pattern pattern;

    @Override
    public void initialize(ValidEnum annotation) {
        try {
            this.pattern = Pattern.compile(annotation.regexp());
        } catch (PatternSyntaxException e) {
            throw new IllegalArgumentException(
                    String.format("Regex syntax is not valid: %s", annotation.regexp()));
        }
    }

    @Override
    public boolean isValid(Enum<?> anEnum, ConstraintValidatorContext context) {
        if (anEnum == null) {
            return true;
        }
        Matcher matcher = pattern.matcher(anEnum.name());
        return matcher.matches();
    }
}
