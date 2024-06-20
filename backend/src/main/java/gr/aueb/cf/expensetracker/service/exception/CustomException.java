package gr.aueb.cf.expensetracker.service.exception;

import lombok.Data;
import org.springframework.http.HttpStatus;

import java.io.Serial;

@Data
public class CustomException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = 1L;
    private final HttpStatus statusCode;
    private final String apiMessage;

    public CustomException(HttpStatus statusCode, String apiMessage) {
        super(apiMessage);
        this.statusCode = statusCode;
        this.apiMessage = apiMessage;
    }
}
