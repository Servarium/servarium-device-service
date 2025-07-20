package app.servarium.adapter.restapi.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Стандартный ответ при ошибке")
public record ErrorResponse(
        @Schema(description = "Краткое описание ошибки", example = "Ошибка валидации запроса")
        String title,

        @Schema(description = "Документированный код ошибки", example = "d001")
        String code,

        @Schema(description = "HTTP статус ошибки", example = "400")
        int status,

        @Schema(description = "Детальное описание ошибки", example = "[{'field':'cpuName', 'message':'must not be blank'}]")
        Object detail
) {
}