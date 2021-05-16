package in.rcardin.demo.api.cars;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice(assignableTypes = CarController.class)
public class CarControllerAdvice {
  
  @ExceptionHandler(value = {NoSuchElementException.class})
  @ResponseStatus(value = HttpStatus.NOT_FOUND)
  public ErrorMessage handleNoSuchElementException(NoSuchElementException ex) {
    return new ErrorMessage(
        LocalDateTime.now(),
        ex.getMessage()
    );
  }
  
  @ExceptionHandler(value = {MethodArgumentNotValidException.class})
  @ResponseStatus(value = HttpStatus.BAD_REQUEST)
  public ErrorMessage handleMethodArgumentNotValid(MethodArgumentNotValidException ex) {
    return new ErrorMessage(
        LocalDateTime.now(),
        buildReadableMessage(ex)
    );
  }
  
  private String buildReadableMessage(MethodArgumentNotValidException ex) {
    List<String> errors = new ArrayList<>();
    for (FieldError error : ex.getBindingResult().getFieldErrors()) {
      errors.add(error.getField() + ": " + error.getDefaultMessage());
    }
    for (ObjectError error : ex.getBindingResult().getGlobalErrors()) {
      errors.add(error.getObjectName() + ": " + error.getDefaultMessage());
    }
    return errors.toString();
  }
  
  static class ErrorMessage {
    private final LocalDateTime date;
    private final String description;
  
    @JsonCreator
    ErrorMessage(
        @JsonProperty("date") LocalDateTime date,
        @JsonProperty("description") String description) {
      this.date = date;
      this.description = description;
    }
  
    public LocalDateTime getDate() {
      return date;
    }
  
    public String getDescription() {
      return description;
    }
  }
}
