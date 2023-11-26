package vn.dangdnh.exception;

import java.io.Serial;

public class EntityExistsException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = 9852736100201853L;

    public EntityExistsException() {
        super("Entity already exists");
    }

    public EntityExistsException(String message) {
        super(message);
    }
}
