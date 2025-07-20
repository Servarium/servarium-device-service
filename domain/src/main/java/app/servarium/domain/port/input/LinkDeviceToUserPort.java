package app.servarium.domain.port.input;

import app.servarium.domain.shared.params.LinkDeviceParams;

public interface LinkDeviceToUserPort {
    void execute(LinkDeviceParams params);
}