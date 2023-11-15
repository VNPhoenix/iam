package vn.dangdnh.exception;

import java.io.Serial;

public class EntityAlreadyExistsException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = 9852736100201853L;

    public EntityAlreadyExistsException() {
        super("Entity already exists");
    }

    public EntityAlreadyExistsException(String message) {
        super(message);
    }
}
