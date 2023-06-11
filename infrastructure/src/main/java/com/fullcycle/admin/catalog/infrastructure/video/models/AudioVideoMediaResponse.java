package com.fullcycle.admin.catalog.infrastructure.video.models;

import com.fasterxml.jackson.annotation.JsonProperty;

public record AudioVideoMediaResponse(
        @JsonProperty("id") String id,
        @JsonProperty("checksum") String checksum,
        @JsonProperty("checksum") String name,
        @JsonProperty("location") String rawLocation,
        @JsonProperty("encoded_location") String encodedLocation,
        @JsonProperty("mime_type") String status
) {
}
