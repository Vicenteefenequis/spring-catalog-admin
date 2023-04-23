package com.fullcycle.admin.catalog.domain.castmember;

import com.fullcycle.admin.catalog.domain.pagination.Pagination;
import com.fullcycle.admin.catalog.domain.pagination.SearchQuery;

import java.util.Optional;

public interface CastMemberGateway {
    CastMember create(CastMember genre);

    void deleteById(CastMemberID genreId);

    Optional<CastMember> findById(CastMemberID genreId);

    CastMember update(CastMember genre);

    Pagination<CastMember> findAll(SearchQuery aQuery);
}
