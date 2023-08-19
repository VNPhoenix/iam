package vn.dangdnh.controller.advice;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import vn.dangdnh.definition.message.exception.ExceptionMessages;
import vn.dangdnh.dto.apierror.ApiError;
import vn.dangdnh.dto.apierror.ApiSubError;
import vn.dangdnh.exception.AuthenticationException;
import vn.dangdnh.exception.DataConflictException;
import vn.dangdnh.exception.EntityAlreadyExistsException;
import vn.dangdnh.exception.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@ControllerAdvice
public class AdvisorController {

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ApiError> handleEntityNotFoundException(EntityNotFoundException e) {
        ApiError apiError = new ApiError(HttpStatus.NOT_FOUND, ExceptionMessages.ENTITY_NOT_FOUND);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(apiError);
    }

    @ExceptionHandler(EntityAlreadyExistsException.class)
    public ResponseEntity<ApiError> handleEntityAlreadyExistsException(EntityAlreadyExistsException e) {
        ApiError apiError = new ApiError(HttpStatus.UNPROCESSABLE_ENTITY, ExceptionMessages.ENTITY_ALREADY_EXISTS);
        apiError.addSubError(new ApiSubError(e.getMessage()));
        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(apiError);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ApiError> handleConstraintViolationException(ConstraintViolationException e) {
        ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, ExceptionMessages.Validation.CONSTRAINT_VIOLATION);
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
                .collect(Collectors.toList());
        ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, ExceptionMessages.Binding.METHOD_ARGUMENT_NOT_VALID);
        apiError.setSubErrors(subErrors);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiError);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ApiError> handleHttpMessageNotReadableException(HttpMessageNotReadableException e) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, ExceptionMessages.Binding.HTTP_MESSAGE_NOT_READABLE);
        Throwable cause = e.getCause();
        if (cause instanceof JsonParseException) {
            JsonParseException jpe = (JsonParseException) cause;
            apiError.addSubError(new ApiSubError(jpe.getOriginalMessage()));
        } else if (cause instanceof JsonMappingException) {
            Class<?> clazz = cause.getClass();
            Method method = clazz.getMethod("getPath");
            @SuppressWarnings("unchecked")
            List<JsonMappingException.Reference> references = (List<JsonMappingException.Reference>) method.invoke(cause);
            if (Objects.nonNull(references) && !references.isEmpty()) {
                for (JsonMappingException.Reference reference : references) {
                    String message = String.format("Field is invalid: %s", reference.getFieldName());
                    apiError.addSubError(new ApiSubError(message));
                }
            }
        } else {
            apiError = new ApiError(HttpStatus.BAD_REQUEST, ExceptionMessages.Binding.HTTP_MESSAGE_NOT_READABLE, e);
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiError);
    }

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<ApiError> handleAuthenticationException(AuthenticationException e) {
        ApiError apiError = new ApiError(HttpStatus.UNAUTHORIZED, ExceptionMessages.Security.AUTHENTICATION_FAILED);
        apiError.addSubError(new ApiSubError(e.getMessage()));
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(apiError);
    }

    @ExceptionHandler(DataConflictException.class)
    public ResponseEntity<ApiError> handleOperationNotAllowedException(DataConflictException e) {
        ApiError apiError = new ApiError(HttpStatus.CONFLICT, ExceptionMessages.OPERATION_NOT_ALLOWED);
        apiError.addSubError(new ApiSubError(ExceptionMessages.ROLE_ALREADY_IN_USE));
        return ResponseEntity.status(HttpStatus.CONFLICT).body(apiError);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiError> handleGenericException(Exception e) {
        ApiError apiError = new ApiError(HttpStatus.INTERNAL_SERVER_ERROR);
        System.out.println(e);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(apiError);
    }
}
