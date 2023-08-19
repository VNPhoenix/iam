package vn.dangdnh.exception;

public class EntityNotFoundException extends RuntimeException {

    private static final long serialVersionUID = 7252336100241854L;

    public EntityNotFoundException(String message) {
        super(message);
    }
}
