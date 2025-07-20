package app.servarium.adapter.restapi.common;

import app.servarium.adapter.restapi.dto.response.ErrorResponse;
import app.servarium.core.exception.*;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Path;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.util.List;

@RestControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandler {
    private final ResponseEntityFactory responseFactory;

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handle(MethodArgumentNotValidException e, WebRequest r) {
        List<ConstraintViolation> violations = e.getFieldErrors()
                .stream()
                .map(error ->
                        ConstraintViolation.of(error.getField(), error.getDefaultMessage())
                )
                .toList();

        return responseFactory.create(new ErrorParams("d001", violations, HttpStatus.BAD_REQUEST, r));
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ErrorResponse> handle(ConstraintViolationException ex, WebRequest r) {
        List<ConstraintViolation> violations = ex.getConstraintViolations().stream()
                .map(e ->
                        ConstraintViolation.of(extractSimplePropertyName(e.getPropertyPath()), e.getMessage())
                )
                .toList();

        return responseFactory.create(new ErrorParams("d001", violations, HttpStatus.BAD_REQUEST, r));
    }

    private String extractSimplePropertyName(Path propertyPath) {
        String name = null;
        for (Path.Node node : propertyPath) {
            name = node.getName();
        }
        return name != null
                ? name
                : propertyPath.toString();
    }

    @ExceptionHandler(DeviceAlreadyExistsException.class)
    public ResponseEntity<ErrorResponse> handle(DeviceAlreadyExistsException e, WebRequest r) {
        return responseFactory.create(new ErrorParams("d010", e.getMessage(), HttpStatus.CONFLICT, r));
    }

    @ExceptionHandler(DeviceNotFoundException.class)
    public ResponseEntity<ErrorResponse> handle(DeviceNotFoundException e, WebRequest r) {
        return responseFactory.create(new ErrorParams("d011", e.getMessage(), HttpStatus.NOT_FOUND, r));
    }

    @ExceptionHandler(DeviceNotLinkedToUserException.class)
    public ResponseEntity<ErrorResponse> handle(DeviceNotLinkedToUserException e, WebRequest r) {
        return responseFactory.create(new ErrorParams("d020", e.getMessage(), HttpStatus.NOT_FOUND, r));
    }

    @ExceptionHandler(ConnectionKeyInvalidException.class)
    public ResponseEntity<ErrorResponse> handle(ConnectionKeyInvalidException e, WebRequest r) {
        return responseFactory.create(new ErrorParams("d021", e.getMessage(), HttpStatus.BAD_REQUEST, r));
    }

    @ExceptionHandler(DeviceAlreadyLinkedToUserException.class)
    public ResponseEntity<ErrorResponse> handle(DeviceAlreadyLinkedToUserException e, WebRequest r) {
        return responseFactory.create(new ErrorParams("d022", e.getMessage(), HttpStatus.CONFLICT, r));
    }
}
