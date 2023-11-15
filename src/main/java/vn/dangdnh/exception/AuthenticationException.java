package vn.dangdnh.exception;

import java.io.Serial;

public class AuthenticationException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = 8252436100241953L;

    public AuthenticationException() {
        super("Unauthorized");
    }

    public AuthenticationException(String message) {
        super(message);
    }
}
