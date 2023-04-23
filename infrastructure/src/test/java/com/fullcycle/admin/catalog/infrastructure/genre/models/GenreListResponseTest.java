package com.fullcycle.admin.catalog.infrastructure.genre.models;

import com.fullcycle.admin.catalog.JacksonTest;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.json.JacksonTester;

import java.time.Instant;

@JacksonTest
class GenreListResponseTest {

    @Autowired
    private JacksonTester<GenreListResponse> json;

    @Test
    public void testMarshall() throws Exception {
        final var expectedId = "123";
        final var expectedName = "Ação";
        final var expectedActive = true;
        final var expectedCreatedAt = Instant.now();
        final var expectedDeletedAt = Instant.now();


        final var response = new GenreListResponse(
                expectedId,
                expectedName,
                expectedActive,
                expectedCreatedAt,
                expectedDeletedAt
        );

        final var actualJson = this.json.write(response);
        Assertions.assertThat(actualJson)
                .hasJsonPathValue("$.id", expectedId)
                .hasJsonPathValue("$.name", expectedName)
                .hasJsonPathValue("$.is_active", expectedActive)
                .hasJsonPathValue("$.created_at", expectedCreatedAt.toString())
                .hasJsonPathValue("$.deleted_at", expectedDeletedAt.toString());
    }

}