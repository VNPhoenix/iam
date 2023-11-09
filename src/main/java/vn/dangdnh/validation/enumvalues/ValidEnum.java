package vn.dangdnh.validation.enumvalues;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import vn.dangdnh.validation.enumvalues.impl.EnumConstraintValidator;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = EnumConstraintValidator.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidEnum {

    String regexp() default "[a-zA-Z0-9_]";

    String message() default "Value is invalid!";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
