package com.fullcycle.admin.catalog.infrastructure.castmember;

import com.fullcycle.admin.catalog.domain.castmember.CastMember;
import com.fullcycle.admin.catalog.domain.castmember.CastMemberGateway;
import com.fullcycle.admin.catalog.domain.castmember.CastMemberID;
import com.fullcycle.admin.catalog.domain.pagination.Pagination;
import com.fullcycle.admin.catalog.domain.pagination.SearchQuery;
import com.fullcycle.admin.catalog.infrastructure.castmember.persistence.CastMemberRepository;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Optional;

@Component
public class CastMemberMySQLGateway implements CastMemberGateway {

    private  final CastMemberRepository castMemberRepository;

    public CastMemberMySQLGateway(final CastMemberRepository castMemberRepository) {
        this.castMemberRepository = Objects.requireNonNull(castMemberRepository);
    }

    @Override
    public CastMember create(final CastMember genre) {
        return null;
    }

    @Override
    public void deleteById(final CastMemberID genreId) {

    }

    @Override
    public Optional<CastMember> findById(final CastMemberID genreId) {
        return Optional.empty();
    }

    @Override
    public CastMember update(final CastMember genre) {
        return null;
    }

    @Override
    public Pagination<CastMember> findAll(final SearchQuery aQuery) {
        return null;
    }
}
