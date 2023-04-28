package com.fullcycle.admin.catalog.infrastructure.castmember.models;

import com.fullcycle.admin.catalog.domain.castmember.CastMemberType;

public record UpdateCastMemberRequest(
        String name,
        CastMemberType type
        ) {
}
