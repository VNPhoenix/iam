package vn.dangdnh.validation.useraccount;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import vn.dangdnh.validation.useraccount.impl.PasswordConstraintValidator;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = PasswordConstraintValidator.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidPassword {

    int minLength() default PasswordConstraintValidator.MIN_LENGTH;

    int maxLength() default PasswordConstraintValidator.MAX_LENGTH;

    String message() default "Password is invalid!";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
