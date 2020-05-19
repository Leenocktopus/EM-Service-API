package leandoer.example.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.LinkedHashMap;
import java.util.Map;

@ControllerAdvice
public class Handler extends ResponseEntityExceptionHandler {


    @ExceptionHandler(DuplicateSaveException.class)
    public final ResponseEntity<ErrorResponse> handleDuplicateSaveException(DuplicateSaveException e, WebRequest request) {
        Map<String, String> details = new LinkedHashMap<>();
        details.put("cause", DuplicateSaveException.cause);
        details.put("resource", e.getResource());
        ErrorResponse response = new ErrorResponse(details);
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(NoSuchResourceException.class)
    public final ResponseEntity<ErrorResponse> handleNoSuchResourceException(NoSuchResourceException e, WebRequest request) {
        Map<String, String> details = new LinkedHashMap<>();
        details.put("cause", NoSuchResourceException.cause);
        details.put("id", e.getId());
        details.put("resource", e.getResource());
        ErrorResponse response = new ErrorResponse(details);
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }
}
