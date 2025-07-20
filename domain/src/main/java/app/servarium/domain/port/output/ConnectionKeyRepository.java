package app.servarium.domain.port.output;

import app.servarium.domain.model.ConnectionKey;

import java.util.Optional;

public interface ConnectionKeyRepository {
    Optional<ConnectionKey> getByKeyValue(String keyValue);

    void save(ConnectionKey key);
}
