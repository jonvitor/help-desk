package models.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

import java.util.List;

@SuperBuilder
public class ValidationError extends StandardError {

    @Getter
    private List<FieldError> errors;

    @Getter
    @AllArgsConstructor
    private static class FieldError {
        private String field;
        private String message;
    }

    public void addError(String field, String message) {
        this.errors.add(new FieldError(field, message));
    }
}
