package vn.dangdnh.exception;

import java.io.Serial;

public class EntityNotFoundException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = 7252336100241854L;

    public EntityNotFoundException() {
        super("Entity was not found");
    }

    public EntityNotFoundException(String message) {
        super(message);
    }
}
