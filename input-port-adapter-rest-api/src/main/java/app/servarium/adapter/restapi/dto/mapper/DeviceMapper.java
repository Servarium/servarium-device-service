package app.servarium.adapter.restapi.dto.mapper;

import app.servarium.adapter.restapi.dto.request.RegisterDeviceRequest;
import app.servarium.adapter.restapi.dto.request.UpdateDeviceConfigRequest;
import app.servarium.domain.model.value.HardwareConfig;
import app.servarium.domain.model.value.SoftwareConfig;
import app.servarium.domain.shared.params.DevicePayload;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.UUID;

@Mapper(componentModel = "spring")
public interface DeviceMapper {

    @Mapping(target = "softwareConfig.osType", source = "osType")
    @Mapping(target = "softwareConfig.osVersion", source = "osVersion")
    @Mapping(target = "hardwareConfig.cpuName", source = "cpuName")
    @Mapping(target = "hardwareConfig.gpuName", source = "gpuName")
    @Mapping(target = "hardwareConfig.ramAmount", source = "ramAmount")
    @Mapping(target = "hardwareConfig.mainDiskName", source = "mainDiskName")
    @Mapping(target = "hardwareConfig.machineName", source = "machineName")
    DevicePayload toDomainPayload(RegisterDeviceRequest req);

    default DevicePayload toDomainPayload(UUID deviceId, UpdateDeviceConfigRequest req) {
        return DevicePayload.builder()
                .deviceId(deviceId)
                .softwareConfig(new SoftwareConfig(req.getOsType(), req.getOsVersion()))
                .hardwareConfig(HardwareConfig.builder()
                        .gpuName(req.getGpuName())
                        .cpuName(req.getCpuName())
                        .ramAmount(req.getRamAmount())
                        .mainDiskName(req.getMainDiskName())
                        .machineName(req.getMachineName())
                        .build())
                .build();
    }
}
