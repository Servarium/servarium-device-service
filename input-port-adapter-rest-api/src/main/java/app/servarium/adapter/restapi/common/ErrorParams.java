package app.servarium.adapter.restapi.common;

import org.springframework.http.HttpStatus;
import org.springframework.web.context.request.WebRequest;

public record ErrorParams(
        String code,
        Object detail,
        HttpStatus status,
        WebRequest request
) {
}
