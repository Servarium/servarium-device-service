package app.servarium.adapter.restapi.dto.mapper;

import app.servarium.adapter.restapi.dto.request.LinkDeviceRequest;
import app.servarium.adapter.restapi.dto.request.UpdateUserDeviceRequest;
import app.servarium.adapter.restapi.dto.response.DeviceConfigResponse;
import app.servarium.adapter.restapi.dto.response.UserDeviceItemResponse;
import app.servarium.domain.shared.params.LinkDeviceParams;
import app.servarium.domain.shared.params.UpdateUserDeviceParams;
import app.servarium.domain.shared.result.DeviceConfigData;
import app.servarium.domain.shared.result.UserDeviceItemData;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;
import java.util.UUID;

@Mapper(componentModel = "spring")
public interface UserDeviceMapper {

    @Mapping(target = "osType", source = "softwareConfig.osType")
    @Mapping(target = "osVersion", source = "softwareConfig.osVersion")
    UserDeviceItemResponse toItem(UserDeviceItemData domain);

    List<UserDeviceItemResponse> toItemList(List<UserDeviceItemData> domains);

    @Mapping(target = "osType", source = "software.osType")
    @Mapping(target = "osVersion", source = "software.osVersion")
    @Mapping(target = "cpuName", source = "hardware.cpuName")
    @Mapping(target = "gpuName", source = "hardware.gpuName")
    @Mapping(target = "ramAmount", source = "hardware.ramAmount")
    @Mapping(target = "mainDiskName", source = "hardware.mainDiskName")
    @Mapping(target = "machineName", source = "hardware.machineName")
    DeviceConfigResponse toConfig(DeviceConfigData domain);

    default LinkDeviceParams toDomain(long userId, LinkDeviceRequest req) {
        return LinkDeviceParams.builder()
                .userId(userId)
                .keyValue(req.getKeyValue())
                .aliasName(req.getAliasName())
                .build();
    }

    default UpdateUserDeviceParams toDomain(long userId, UUID deviceId, UpdateUserDeviceRequest req) {
        return UpdateUserDeviceParams.builder()
                .userId(userId)
                .deviceId(deviceId)
                .newAliasName(req.getNewAliasName())
                .build();
    }
}
