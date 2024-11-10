package br.com.jointecnologia.crud.exceptions;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Represents the error structure returned by the API.
 *
 * <p>This class is used to provide detailed information about errors that occur during request processing
 * in the API. It includes the HTTP status, a general error message, a list of specific errors (if any),
 * and the timestamp of when the error occurred.</p>
 *
 * @version 1.0
 * @since 2024-11-09
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApiError {

    /**
     * The HTTP status associated with the error
     */
    private HttpStatus status;

    /**
     * General error message
     */
    private String message;

    /**
     * List of detailed error messages
     */
    @JsonFormat(with = JsonFormat.Feature.WRITE_SINGLE_ELEM_ARRAYS_UNWRAPPED)
    private List<String> errors;

    /**
     * The date and time when the error occurred
     */
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime dateTime;

}
