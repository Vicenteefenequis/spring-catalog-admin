package com.fullcycle.admin.catalog.infrastructure.castmember.models;

public record CastMemberResponse(
        String id,
        String name,
        String type,
        String createdAt,
        String updatedAt
) {
}
