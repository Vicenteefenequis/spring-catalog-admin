package com.fullcycle.admin.catalog.domain.video;

import com.fullcycle.admin.catalog.domain.events.DomainEvent;
import com.fullcycle.admin.catalog.domain.utils.InstantUtils;

import java.time.Instant;

public record VideoMediaCreated(
        String resourceId,
        String filePath,
        Instant occurredOn
) implements DomainEvent {
    public VideoMediaCreated(final String resourceId, final String filePath) {
        this(resourceId, filePath, InstantUtils.now());
    }

}
