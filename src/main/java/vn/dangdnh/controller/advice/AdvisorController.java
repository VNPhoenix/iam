package vn.dangdnh.controller.advice;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import vn.dangdnh.dto.apierror.ApiError;
import vn.dangdnh.dto.apierror.ApiSubError;
import vn.dangdnh.exception.AuthenticationException;
import vn.dangdnh.exception.EntityExistsException;
import vn.dangdnh.exception.EntityNotFoundException;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

@ControllerAdvice
public class AdvisorController {

    private static final Logger logger = LogManager.getLogger(AdvisorController.class);

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ApiError> handleEntityNotFoundException(EntityNotFoundException e) {
        ApiError apiError = new ApiError(HttpStatus.NOT_FOUND, "Entity was not found");
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(apiError);
    }

    @ExceptionHandler(EntityExistsException.class)
    public ResponseEntity<ApiError> handleEntityExistsException(EntityExistsException e) {
        ApiError apiError = new ApiError(HttpStatus.UNPROCESSABLE_ENTITY, "Entity already exists");
        apiError.addSubError(new ApiSubError(e.getMessage()));
        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(apiError);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ApiError> handleConstraintViolationException(ConstraintViolationException e) {
        ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, "Constraint violation");
        for (ConstraintViolation<?> violation : e.getConstraintViolations()) {
            ApiSubError subError = new ApiSubError(violation.getMessage());
            apiError.addSubError(subError);
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiError);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ApiError> handleIllegalArgumentException(IllegalArgumentException e) {
        ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, e.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiError);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiError> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        List<ApiSubError> subErrors = e.getBindingResult().getAllErrors().stream()
                .filter(FieldError.class::isInstance)
                .map(FieldError.class::cast)
                .map(fieldError -> new ApiSubError(fieldError.getDefaultMessage()))
                .toList();
        ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, "Method argument is not valid");
        apiError.setSubErrors(subErrors);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiError);
    }

    @SuppressWarnings("unchecked")
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ApiError> handleHttpMessageNotReadableException(HttpMessageNotReadableException e)
            throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, "Http message is not readable");
        Throwable cause = e.getCause();
        if (cause instanceof JsonParseException jpe) {
            apiError.addSubError(new ApiSubError(jpe.getOriginalMessage()));
        } else if (cause instanceof JsonMappingException) {
            Class<?> clazz = cause.getClass();
            Method method = clazz.getMethod("getPath");
            List<JsonMappingException.Reference> references = (List<JsonMappingException.Reference>) method.invoke(cause);
            if (references != null && !references.isEmpty()) {
                for (JsonMappingException.Reference reference : references) {
                    String message = String.format("Field is invalid: %s", reference.getFieldName());
                    apiError.addSubError(new ApiSubError(message));
                }
            }
        } else {
            apiError = new ApiError(HttpStatus.BAD_REQUEST, "Http message is not readable", e);
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiError);
    }

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<ApiError> handleAuthenticationException(AuthenticationException e) {
        ApiError apiError = new ApiError(HttpStatus.UNAUTHORIZED, "Unauthorized");
        apiError.addSubError(new ApiSubError(e.getMessage()));
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(apiError);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiError> handleGenericException(Exception e) {
        logger.error(e);
        ApiError apiError = new ApiError(HttpStatus.INTERNAL_SERVER_ERROR);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(apiError);
    }
}
