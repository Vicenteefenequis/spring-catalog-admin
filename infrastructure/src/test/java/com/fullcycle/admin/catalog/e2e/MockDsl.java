package com.fullcycle.admin.catalog.e2e;

import com.fullcycle.admin.catalog.domain.Identifier;
import com.fullcycle.admin.catalog.domain.category.CategoryID;
import com.fullcycle.admin.catalog.domain.genre.GenreID;
import com.fullcycle.admin.catalog.infrastructure.category.models.CategoryResponse;
import com.fullcycle.admin.catalog.infrastructure.category.models.CreateCategoryRequest;
import com.fullcycle.admin.catalog.infrastructure.category.models.UpdateCategoryRequest;
import com.fullcycle.admin.catalog.infrastructure.configuration.json.Json;
import com.fullcycle.admin.catalog.infrastructure.genre.models.CreateGenreRequest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;
import java.util.function.Function;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public interface MockDsl {
    MockMvc mvc();

    default ResultActions deleteACategory(final Identifier anId) throws Exception {
        return this.delete("/categories/", anId);
    }

    default CategoryID givenACategory(final String aName, final String aDescription, final boolean isActive) throws Exception {
        final var actualId = this.given("/categories", new CreateCategoryRequest(aName, aDescription, isActive));
        return CategoryID.from(actualId);
    }


    default ResultActions listCategories(final int page, final int perPage, final String search) throws Exception {
        return listCategories(page, perPage, search, "", "");
    }

    default ResultActions listCategories(final int page, final int perPage) throws Exception {
        return listCategories(page, perPage, "", "", "");
    }

    default ResultActions listCategories(final int page, final int perPage, final String search, final String sort, final String direction) throws Exception {
        return this.list("/categories", page, perPage, search, sort, direction);
    }


    default ResultActions listGenres(final int page, final int perPage, final String search) throws Exception {
        return listGenres(page, perPage, search, "", "");
    }

    default ResultActions listGenres(final int page, final int perPage) throws Exception {
        return listGenres(page, perPage, "", "", "");
    }

    default ResultActions listGenres(final int page, final int perPage, final String search, final String sort, final String direction) throws Exception {
        return this.list("/genres", page, perPage, search, sort, direction);
    }

    default CategoryResponse retrieveCategory(final Identifier anId) throws Exception {
        return this.retrieve("/categories/", anId, CategoryResponse.class);
    }

    default ResultActions updateCategory(final Identifier anId, final UpdateCategoryRequest aRequest) throws Exception {
        return this.update("/categories/", anId, aRequest);
    }

    default GenreID givenAGenre(final String aName, final boolean isActive, final List<CategoryID> categories) throws Exception {
        final var actualId = this.given("/genres", new CreateGenreRequest(aName, mapTo(categories, CategoryID::getValue), isActive));
        return GenreID.from(actualId);
    }

    default <A, D> List<D> mapTo(final List<A> actual, final Function<A, D> mapper) {
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


    private ResultActions list(final String url, final int page, final int perPage, final String search, final String sort, final String direction) throws Exception {
        final var aRequest = get(url).queryParam("page", String.valueOf(page)).queryParam("perPage", String.valueOf(perPage)).queryParam("search", search).queryParam("sort", sort).queryParam("dir", direction).contentType(MediaType.APPLICATION_JSON);
        return this.mvc().perform(aRequest);
    }


    private <T> T retrieve(final String url, final Identifier anId, final Class<T> clazz) throws Exception {
        final var aRequest = get(url + anId.getValue()).contentType(MediaType.APPLICATION_JSON);

        final var actualResponse = this.mvc().perform(aRequest).andExpect(status().isOk()).andReturn().getResponse().getContentAsString();

        return Json.readValue(actualResponse, clazz);
    }

    private ResultActions delete(final String url, final Identifier anId) throws Exception {
        return this.mvc().perform(MockMvcRequestBuilders.delete(url + anId.getValue())
                .contentType(MediaType.APPLICATION_JSON));
    }

    private ResultActions update(final String url, final Identifier anId, final Object aRequestBody) throws Exception {
        final var aRequest = put(url + anId.getValue())
                .contentType(MediaType.APPLICATION_JSON).content(Json.writeValueAsString(aRequestBody));

        return this.mvc().perform(aRequest);
    }


}
