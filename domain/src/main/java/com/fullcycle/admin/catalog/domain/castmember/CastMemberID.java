package com.fullcycle.admin.catalog.domain.castmember;

import com.fullcycle.admin.catalog.domain.Identifier;
import com.fullcycle.admin.catalog.domain.utils.IdUtils;

import java.util.Objects;

public class CastMemberID extends Identifier {

    private final String value;

    private CastMemberID(String value) {
        this.value = Objects.requireNonNull(value);
    }


    public static CastMemberID from(final String anId) {
        return new CastMemberID(anId);
    }

    public static CastMemberID unique() {
        return CastMemberID.from(IdUtils.uuid());
    }

    @Override
    public String getValue() {
        return this.value;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final CastMemberID that = (CastMemberID) o;
        return Objects.equals(getValue(), that.getValue());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getValue());
    }
}
