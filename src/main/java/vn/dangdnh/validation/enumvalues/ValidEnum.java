package vn.dangdnh.validation.enumvalues;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import vn.dangdnh.validation.enumvalues.impl.EnumConstraintValidator;

import java.lang.annotation.*;

@Documented
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = EnumConstraintValidator.class)
public @interface ValidEnum {

    String regexp() default "[a-zA-Z0-9_]";

    String message() default "Value is invalid!";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
