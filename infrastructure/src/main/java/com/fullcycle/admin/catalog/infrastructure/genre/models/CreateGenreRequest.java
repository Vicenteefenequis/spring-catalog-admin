package com.fullcycle.admin.catalog.infrastructure.genre.models;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Collections;
import java.util.List;

public record CreateGenreRequest(
        @JsonProperty("name") String name,
        @JsonProperty("categories_ids") List<String> categories,
        @JsonProperty("is_active") Boolean active
) {

    public Boolean isActive() {
        return active != null ? active : true;
    }

    public List<String> categories() {
        return this.categories != null ? this.categories : Collections.emptyList();
    }
}
