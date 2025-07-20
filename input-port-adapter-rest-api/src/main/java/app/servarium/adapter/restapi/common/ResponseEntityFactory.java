package app.servarium.adapter.restapi.common;

import app.servarium.adapter.restapi.dto.response.ErrorResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ResponseEntityFactory {
    private final MessageSource messageSource;

    public ResponseEntity<ErrorResponse> create(ErrorParams ep) {
        final int statusCode = ep.status().value();
        return ResponseEntity
                .status(statusCode)
                .body(new ErrorResponse(
                        messageSource.getMessage(ep.code(), null, ep.request().getLocale()),
                        ep.code(),
                        statusCode,
                        ep.detail()));
    }
}
