package vn.dangdnh.exception;

import java.io.Serial;

public class DataConflictException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = 7352736108201851L;

    public DataConflictException(String message) {
        super(message);
    }
}
