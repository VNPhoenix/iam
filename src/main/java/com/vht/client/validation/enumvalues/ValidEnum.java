package com.vht.client.validation.enumvalues;

import com.vht.client.validation.enumvalues.impl.EnumConstraintValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
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
