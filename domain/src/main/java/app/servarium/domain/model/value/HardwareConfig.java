package app.servarium.domain.model.value;

import lombok.Builder;

@Builder
public record HardwareConfig(
        String cpuName,
        String gpuName,
        Float ramAmount,
        String mainDiskName,
        String machineName
) {
}