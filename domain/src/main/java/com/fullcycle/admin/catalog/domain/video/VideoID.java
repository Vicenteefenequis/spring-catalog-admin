package com.fullcycle.admin.catalog.domain.video;

import com.fullcycle.admin.catalog.domain.Identifier;

import java.util.Objects;
import java.util.UUID;

public class VideoID extends Identifier {

    private final String value;

    public VideoID(String value) {
        Objects.requireNonNull(value);
        this.value = value;
    }


    public static VideoID from(final String anId) {
        return new VideoID(anId.toLowerCase());
    }

    public static VideoID from(final UUID anId) {
        return  VideoID.from(anId.toString());
    }

    public static VideoID unique() {
        return VideoID.from(UUID.randomUUID());
    }


    @Override
    public String getValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final VideoID that = (VideoID) o;
        return Objects.equals(getValue(), that.getValue());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getValue());
    }
}
