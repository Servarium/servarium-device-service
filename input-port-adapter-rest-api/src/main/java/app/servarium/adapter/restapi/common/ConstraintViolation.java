package app.servarium.adapter.restapi.common;

import lombok.Value;

@Value(staticConstructor = "of")
public class ConstraintViolation {
    String field;

    String message;
}
