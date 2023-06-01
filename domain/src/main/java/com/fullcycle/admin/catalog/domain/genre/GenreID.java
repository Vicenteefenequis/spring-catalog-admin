package com.fullcycle.admin.catalog.domain.genre;

import com.fullcycle.admin.catalog.domain.Identifier;
import com.fullcycle.admin.catalog.domain.utils.IdUtils;

import java.util.Objects;

public class GenreID extends Identifier {
    private final String value;

    public GenreID(String value) {
        Objects.requireNonNull(value);
        this.value = value;
    }

    public static GenreID from(final String anId) {
        return new GenreID(anId);
    }

    public static GenreID unique() {
        return GenreID.from(IdUtils.uuid());
    }

    @Override
    public String getValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final GenreID that = (GenreID) o;
        return Objects.equals(getValue(), that.getValue());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getValue());
    }
}
