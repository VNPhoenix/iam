package vn.dangdnh.definition.message.exception;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ExceptionMessages {
    public static final String ENTITY_NOT_FOUND = "Entity was not found";
    public static final String ENTITY_ALREADY_EXISTS = "Entity already exists";
    public static final String USERNAME_ALREADY_EXISTS = "Username already exists";
    public static final String OPERATION_NOT_ALLOWED = "Operation is not allowed";
    public static final String ROLE_ALREADY_IN_USE = "Role is already in use";
    public static final String INVALID_REGEX = "Given regex is invalid: %s";

    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    public static class Binding {
        public static final String METHOD_ARGUMENT_NOT_VALID = "Method argument is not isValid";
        public static final String HTTP_MESSAGE_NOT_READABLE = "Http message is not readable";
    }

    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    public static class Validation {
        public static final String CONSTRAINT_VIOLATION = "Constraint violation";
    }

    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    public static class Security {
        public static final String AUTHENTICATION_FAILED = "Authentication was failed";
        public static final String WRONG_USERNAME = "Username is wrong";
        public static final String WRONG_PASSWORD = "Password is wrong";
    }
}
