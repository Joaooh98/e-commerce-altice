package com.altice.domain.vo;

import java.util.UUID;

public class UuidVO {

    final UUID uuid;

    public UuidVO(final String uuid) {
        this.uuid = uuid != null ? UUID.fromString(uuid) : UUID.randomUUID();
    }

    public UUID getValue() {
        return uuid;
    }
}
