package br.com.jointecnologia.crud.exceptions.handler;

import br.com.jointecnologia.crud.exceptions.ApiError;
import org.springframework.core.annotation.Order;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Global exception handler for the API.
 *
 * <p>This class captures and handles various exceptions thrown during the execution of the API and returns
 * an appropriate response based on the exception type. Exceptions are handled by constructing an {@link ApiError}
 * object containing details such as HTTP status, message, and timestamp.</p>
 *
 * @version 1.0
 * @since 2024-11-09
 */
@RestControllerAdvice
@Order(2)
public class ApiExceptionHandler {

    /**
     * Handles {@link MethodArgumentTypeMismatchException} exceptions.
     *
     * @param ex The thrown exception.
     * @return The formatted error response.
     */
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ApiError> handleArgumentException(final MethodArgumentTypeMismatchException ex) {
        ApiError apiError = new ApiError(
                HttpStatus.BAD_REQUEST,
                "The provided argument type does not match the expected type.",
                List.of(ex.getMessage()),
                LocalDateTime.now()
        );
        return new ResponseEntity<>(apiError, new HttpHeaders(), apiError.getStatus());
    }

    /**
     * Handles {@link IncorrectResultSizeDataAccessException} exceptions.
     *
     * @param ex The thrown exception.
     * @return The formatted error response.
     */
    @ExceptionHandler(IncorrectResultSizeDataAccessException.class)
    public ResponseEntity<ApiError> handleIncorrectResultSizeDataAccessException(final IncorrectResultSizeDataAccessException ex) {
        String detailedMessage = String.format("The query returned an unexpected number of results: %d, but %d was expected.",
                ex.getActualSize(), ex.getExpectedSize());

        ApiError apiError = new ApiError(
                HttpStatus.UNPROCESSABLE_ENTITY,
                detailedMessage,
                List.of("IncorrectResultSizeDataAccessException"),
                LocalDateTime.now()
        );
        return new ResponseEntity<>(apiError, new HttpHeaders(), apiError.getStatus());
    }
}
