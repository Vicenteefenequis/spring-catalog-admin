package com.fullcycle.admin.catalog.e2e;

import com.fullcycle.admin.catalog.domain.category.CategoryID;
import com.fullcycle.admin.catalog.domain.genre.GenreID;
import com.fullcycle.admin.catalog.infrastructure.category.models.CreateCategoryRequest;
import com.fullcycle.admin.catalog.infrastructure.configuration.json.Json;
import com.fullcycle.admin.catalog.infrastructure.genre.models.CreateGenreRequest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.function.Function;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public interface MockDsl {
    MockMvc mvc();

    default CategoryID givenACategory(final String aName, final String aDescription, final boolean isActive) throws Exception {
        final var actualId = this.given("/categories", new CreateCategoryRequest(aName, aDescription, isActive));
        return CategoryID.from(actualId);
    }

    default GenreID givenAGenre(final String aName, final boolean isActive, final List<CategoryID> categories) throws Exception {
        final var actualId = this.given("/genres", new CreateGenreRequest(aName, mapTo(categories, CategoryID::getValue), isActive));
        return GenreID.from(actualId);
    }

    default  <A, D> List<D> mapTo(final List<A> actual, final Function<A, D> mapper) {
        return actual.stream().map(mapper).toList();
    }


    private String given(final String url, final Object body) throws Exception {
        final var aRequest = post(url).contentType(MediaType.APPLICATION_JSON).content(Json.writeValueAsString(body));

        final var actualId = this.mvc()
                .perform(aRequest)
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse().getHeader("Location")
                .replace("%s/".formatted(url), "");

        return actualId;
    }



}
