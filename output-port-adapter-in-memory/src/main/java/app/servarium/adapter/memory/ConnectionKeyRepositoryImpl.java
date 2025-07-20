package app.servarium.adapter.memory;

import app.servarium.domain.model.ConnectionKey;
import app.servarium.domain.port.output.ConnectionKeyRepository;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Component
public class ConnectionKeyRepositoryImpl implements ConnectionKeyRepository {
    private final Map<String, ConnectionKey> keys = new HashMap<>();

    @Override
    public Optional<ConnectionKey> getByKeyValue(String keyValue) {
        return Optional.ofNullable(keys.get(keyValue));
    }

    @Override
    public void save(ConnectionKey key) {
        keys.put(key.getValue(), key);
    }
}
