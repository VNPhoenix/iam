package vn.dangdnh.exception;

public class DataConflictException extends RuntimeException {

    private static final long serialVersionUID = 7352736108201851L;

    public DataConflictException(String message) {
        super(message);
    }
}
