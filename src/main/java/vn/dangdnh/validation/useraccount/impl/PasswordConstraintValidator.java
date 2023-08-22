package vn.dangdnh.validation.useraccount.impl;

import org.passay.*;
import vn.dangdnh.validation.useraccount.ValidPassword;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class PasswordConstraintValidator implements ConstraintValidator<ValidPassword, String> {

    public static final String ALLOWED_CHARACTER_REGEX_PATTERN = "abcdefghijklmnopqrstuvwxyz" + "ABCDEFGHIJKLMNOPQRSTUVWXYZ" + "0123456789"
            + "!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~¡¢£¤¥¦§¨©ª«¬\u00ad®¯°±²³´µ¶·¸¹º»¼½¾¿×÷–—―‗‘’‚‛“”„†‡•…‰′″‹›‼‾⁄⁊₠₡₢₣₤₥₦₧₨₩₪₫€₭₮₯₰₱₲₳₴₵₶₷₸₹₺₻₼₽₾";
    public static final int MIN_LENGTH = 8;
    public static final int MAX_LENGTH = 50;

    private int minLength;
    private int maxLength;

    @Override
    public void initialize(ValidPassword constraintAnnotation) {
        this.minLength = constraintAnnotation.minLength();
        this.maxLength = constraintAnnotation.maxLength();
    }

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        if (Objects.isNull(s)) {
            return true;
        }
        PasswordValidator validator = new PasswordValidator(Arrays.asList(new LengthRule(minLength, maxLength),
                new CharacterRule(EnglishCharacterData.UpperCase, 1),
                new CharacterRule(EnglishCharacterData.LowerCase, 1),
                new CharacterRule(EnglishCharacterData.Digit, 1),
                new CharacterRule(EnglishCharacterData.Special, 1),
                new WhitespaceRule()));
        RuleResult result = validator.validate(new PasswordData(s));
        if (result.isValid()) {
            return true;
        }
        List<String> messages = validator.getMessages(result);
        constraintValidatorContext.buildConstraintViolationWithTemplate(messages.toString())
                .addConstraintViolation()
                .disableDefaultConstraintViolation();
        return false;
    }
}
