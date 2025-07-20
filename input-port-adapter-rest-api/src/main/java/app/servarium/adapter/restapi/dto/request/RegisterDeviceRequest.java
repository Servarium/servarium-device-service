package app.servarium.adapter.restapi.dto.request;

import app.servarium.domain.model.value.OSType;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Данные для регистрации устройства в системе")
public class RegisterDeviceRequest {
    @Schema(description = "Идентификатор устройства", example = "123e4567-e89b-12d3-a456-426614174000")
    @NotNull
    private UUID deviceId;

    @Schema(description = "Операционная система устройства", example = "LINUX")
    @NotNull
    private OSType osType;

    @Schema(description = "Версия операционной системы", example = "Ubuntu 24.04.2 LTS")
    @Size(max = 128)
    private String osVersion;

    @Schema(description = "Название процессора", example = "AMD Ryzen Threadripper @ 3.7 MHz, 128 cores")
    @NotBlank
    @Size(max = 128)
    private String cpuName;

    @Schema(description = "Название графического процессора", example = "Nvidia RTX 3080 TI")
    @Size(max = 128)
    private String gpuName;

    @Schema(description = "Объем оперативной памяти, ГБ", example = "128")
    @Digits(integer = 3, fraction = 3)
    @PositiveOrZero
    private Float ramAmount;

    @Schema(description = "Название основного диска", example = "Samsung SSD 850 Evo 250 Gb")
    @Size(max = 128)
    private String mainDiskName;

    @Schema(description = "Имя устройства", example = "HP ProBook 450 G3")
    @Size(max = 128)
    private String machineName;
}