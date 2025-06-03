package dev.kirilliym.weatherforecast.model.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class ErrorResponse {
    private String message;
    private int status;
    private LocalDateTime timestamp;

    public ErrorResponse(String message, HttpStatus status) {
        this.message = message;
        this.status = status.value();
        this.timestamp = LocalDateTime.now();
    }
}
