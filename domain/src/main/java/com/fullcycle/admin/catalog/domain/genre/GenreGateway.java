package com.fullcycle.admin.catalog.domain.genre;

import com.fullcycle.admin.catalog.domain.pagination.Pagination;
import com.fullcycle.admin.catalog.domain.pagination.SearchQuery;

import java.util.Optional;

public interface GenreGateway {
    Genre create(Genre genre);

    void deleteById(GenreID genreId);

    Optional<Genre> findById(GenreID genreId);

    Genre update(Genre genre);

    Pagination<Genre> findAll(SearchQuery aQuery);
}
