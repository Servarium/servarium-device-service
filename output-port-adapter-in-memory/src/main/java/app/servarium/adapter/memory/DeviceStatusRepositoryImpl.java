package app.servarium.adapter.memory;

import app.servarium.domain.port.output.DeviceStatusRepository;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Map;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class DeviceStatusRepositoryImpl implements DeviceStatusRepository {
    @Override
    public Map<UUID, Boolean> getActiveStatusesByDeviceIds(Collection<UUID> deviceIds) {
        return deviceIds.stream()
                .collect(Collectors.toMap(Function.identity(), id -> true));
    }
}