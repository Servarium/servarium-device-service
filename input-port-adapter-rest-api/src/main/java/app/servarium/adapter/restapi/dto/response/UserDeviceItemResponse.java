package app.servarium.adapter.restapi.dto.response;

import app.servarium.domain.model.value.OSType;
import lombok.Builder;

import java.util.UUID;

@Builder
public record UserDeviceItemResponse(
        UUID deviceId,
        String aliasName,
        OSType osType,
        String osVersion,
        boolean isActive
) {
}