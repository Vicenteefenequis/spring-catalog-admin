package com.fullcycle.admin.catalog.infrastructure.castmember.models;

public record CastMemberListResponse(
        String id,
        String name,
        String type,
        String createdAt
) {
}
