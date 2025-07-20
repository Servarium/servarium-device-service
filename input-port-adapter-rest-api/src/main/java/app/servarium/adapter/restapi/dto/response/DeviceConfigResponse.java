package app.servarium.adapter.restapi.dto.response;

import app.servarium.domain.model.value.OSType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@Builder
@Schema(description = "Конфигурация устройства")
public record DeviceConfigResponse(
        @Schema(description = "Операционная система устройства", example = "LINUX")
        OSType osType,

        @Schema(description = "Версия операционной системы", example = "Ubuntu 24.04.2 LTS")
        String osVersion,

        @Schema(description = "Название процессора", example = "AMD Ryzen Threadripper @ 3.7 MHz, 128 cores")
        String cpuName,

        @Schema(description = "Название графического процессора", example = "Nvidia RTX 3080 TI ")
        String gpuName,

        @Schema(description = "Объем оперативной памяти, ГБ", example = "128")
        int ramAmount,

        @Schema(description = "Название основного диска", example = "Samsung SSD 850 Evo 250 Gb")
        String mainDiskName,

        @Schema(description = "Имя устройства", example = "HP ProBook 450 G3")
        String machineName
) {
}
