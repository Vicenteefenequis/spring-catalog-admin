package com.fullcycle.admin.catalog.infrastructure.category.models;

import com.fullcycle.admin.catalog.JacksonTest;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.json.JacksonTester;

@JacksonTest
public class UpdateCategoryRequestTest {

    @Autowired
    private JacksonTester<CategoryResponse> json;


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

}
