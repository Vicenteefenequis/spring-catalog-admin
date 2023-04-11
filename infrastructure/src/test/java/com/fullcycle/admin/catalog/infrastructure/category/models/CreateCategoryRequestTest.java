package com.fullcycle.admin.catalog.infrastructure.category.models;

import com.fullcycle.admin.catalog.JacksonTest;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.json.JacksonTester;

@JacksonTest
class CreateCategoryRequestTest {

    @Autowired
    private JacksonTester<CreateCategoryRequest> json;


    @Test
    public void testUnmarshall() throws Exception {

        final var expectedName = "Filmes";
        final var expectedDescription = "A melhor categoria de filmes";
        final var expectedActive = false;
        final var json = """
                {
                    "name": "%s",
                    "description": "%s",
                    "is_active": %s
                }
                """
                .formatted(
                        expectedName,
                        expectedDescription,
                        expectedActive
                );

        final var actualJson = this.json.parse(json);

        Assertions.assertThat(actualJson)
                .hasFieldOrPropertyWithValue("name", expectedName)
                .hasFieldOrPropertyWithValue("description", expectedDescription)
                .hasFieldOrPropertyWithValue("active", expectedActive);
    }

    @Test
    public void testMarshall() throws Exception {
        final var expectedName = "Filmes";
        final var expectedDescription = "A melhor categoria de filmes";
        final var expectedActive = true;


        final var response = new CreateCategoryRequest(
                expectedName,
                expectedDescription,
                expectedActive
        );

        final var actualJson = this.json.write(response);
        Assertions.assertThat(actualJson)
                .hasJsonPathValue("$.name", expectedName)
                .hasJsonPathValue("$.description", expectedDescription)
                .hasJsonPathValue("$.is_active", expectedActive);
    }
}