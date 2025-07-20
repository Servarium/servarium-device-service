package app.servarium.adapter.restapi.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Данные для привязки устройства к пользователю")
public class LinkDeviceRequest {
    @Schema(description = "Ключ подключения устройства", example = "678e4567-e89b-12d3-a456-426614876981")
    @NotBlank
    @Size(min = 36, max = 36)
    private String keyValue;

    @Schema(description = "Имя подключаемого устройства", example = "Мой ноутбук")
    @NotBlank
    @Size(max = 32)
    private String aliasName;
}
