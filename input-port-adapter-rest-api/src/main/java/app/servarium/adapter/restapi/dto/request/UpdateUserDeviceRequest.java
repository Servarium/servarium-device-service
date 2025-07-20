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
@Schema(description = "Данные для обновления информации о подключенном устройстве пользователя")
public class UpdateUserDeviceRequest {
    @Schema(description = "Новой имя для подключенного устройства", example = "Рабочий ПК")
    @NotBlank
    @Size(max = 32)
    private String newAliasName;
}
