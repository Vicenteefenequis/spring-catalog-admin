package com.fullcycle.admin.catalog.domain.castmember;

import com.fullcycle.admin.catalog.domain.validation.ValidationHandler;
import com.fullcycle.admin.catalog.domain.validation.Validator;

public class CastMemberValidator extends Validator {

    private final CastMember castMember;

    public CastMemberValidator(CastMember castMember, final ValidationHandler aHandler) {
        super(aHandler);
        this.castMember = castMember;
    }

    @Override
    public void validate() {

    }
}