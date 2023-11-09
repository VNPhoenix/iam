package vn.dangdnh.exception;

public class EntityAlreadyExistsException extends RuntimeException {

    private static final long serialVersionUID = 9852736100201853L;

    public EntityAlreadyExistsException() {
        super("Entity already exists");
    }

    public EntityAlreadyExistsException(String message) {
        super(message);
    }
}
