package com.fullcycle.admin.catalog.application.castmember.update;

import com.fullcycle.admin.catalog.domain.castmember.CastMember;
import com.fullcycle.admin.catalog.domain.castmember.CastMemberID;

public record UpdateCastMemberOutput(
        String id
) {
    public static UpdateCastMemberOutput from(final CastMember aCastMember) {
        return from(aCastMember.getId());
    }


    public static UpdateCastMemberOutput from(final CastMemberID anId) {
        return new UpdateCastMemberOutput(anId.getValue());
    }
}
