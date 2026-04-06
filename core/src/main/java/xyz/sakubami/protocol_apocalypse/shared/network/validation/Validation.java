package xyz.sakubami.protocol_apocalypse.shared.network.validation;

import xyz.sakubami.protocol_apocalypse.shared.network.validation.validation.C2SMovementValidationPacket;
import xyz.sakubami.protocol_apocalypse.shared.utils.Vector2f;

import java.util.*;

public class Validation {

    Map<UUID, Vector2f> pending = new HashMap<>();

    public Validation() {
        // loadConfig or something
    }

    public void refresh() {
        this.pending = new HashMap<>();
    }

    public void validateMovement(ValidationPacket packet) {
        if (!(packet instanceof C2SMovementValidationPacket p))
            return;
        Vector2f v = p.getDestination().subtract(p.getLocation());
        float difference = v.x() + v.y();
        if (difference > 20f)
            pending.put(p.getPlayerUUID(), p.getLocation());
    }

    public Map<UUID, Vector2f> fetchPlayerMovementCorrection() { return this.pending; }
}
