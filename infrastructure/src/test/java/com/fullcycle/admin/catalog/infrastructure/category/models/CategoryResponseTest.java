package com.fullcycle.admin.catalog.infrastructure.category.models;

import com.fullcycle.admin.catalog.JacksonTest;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.json.JacksonTester;

import java.time.Instant;

@JacksonTest
public class CategoryResponseTest {

    @Autowired
    private JacksonTester<CategoryResponse> json;

    @Test
    public void testMarshall() throws  Exception{
        final var expectedId = "123";
        final var expectedName = "Filmes";
        final var expectedDescription = "A melhor categoria de filmes";
        final var expectedActive = true;
        final var expectedCreatedAt = Instant.now();
        final var expectedUpdatedAt = Instant.now();
        final var expectedDeletedAt = Instant.now();


        final var response = new CategoryResponse(
                expectedId,
                expectedName,
                expectedDescription,
                expectedActive,
                expectedCreatedAt,
                expectedUpdatedAt,
                expectedDeletedAt
        );

        final var actualJson = this.json.write(response);
        Assertions.assertThat(actualJson)
                .hasJsonPathValue("$.id",expectedId)
                .hasJsonPathValue("$.name",expectedName)
                .hasJsonPathValue("$.description",expectedDescription)
                .hasJsonPathValue("$.is_active",expectedActive)
                .hasJsonPathValue("$.created_at",expectedCreatedAt.toString())
                .hasJsonPathValue("$.updated_at",expectedUpdatedAt.toString())
                .hasJsonPathValue("$.deleted_at",expectedDeletedAt.toString());
    }
}
