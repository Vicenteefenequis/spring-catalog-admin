package com.fullcycle.admin.catalog.e2e.genre;

import com.fullcycle.admin.catalog.E2ETest;
import com.fullcycle.admin.catalog.domain.category.CategoryID;
import com.fullcycle.admin.catalog.e2e.MockDsl;
import com.fullcycle.admin.catalog.infrastructure.genre.persistence.GenreRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.List;

@E2ETest
@Testcontainers
public class GenreE2ETest implements MockDsl {
    @Autowired
    private MockMvc mvc;

    @Autowired
    private GenreRepository genreRepository;

    @Container
    public static final MySQLContainer MYSQL_CONTAINER = new MySQLContainer("mysql:latest").withPassword("root").withUsername("root").withDatabaseName("admin_video");


    @Override
    public MockMvc mvc() {
        return this.mvc;
    }

    @DynamicPropertySource
    public static void setDatasourceProperties(final DynamicPropertyRegistry registry) {
        registry.add("mysql.port", () -> MYSQL_CONTAINER.getMappedPort(3306));
    }


    @Test
    public void asACatalogAdminIShouldBeAbleToCreateANewGenreWithValidValues() throws Exception {
        Assertions.assertTrue(MYSQL_CONTAINER.isRunning());
        Assertions.assertEquals(0, genreRepository.count());

        final var expectedName = "Ação";
        final var expectedIsActive = true;
        final var expectedCategories = List.<CategoryID>of();

        final var actualId = givenAGenre(expectedName, expectedIsActive, expectedCategories);

        final var actualGenre = genreRepository.findById(actualId.getValue()).get();

        Assertions.assertEquals(expectedName, actualGenre.getName());
        Assertions.assertEquals(expectedIsActive, actualGenre.isActive());
        Assertions.assertTrue(expectedCategories.size() == actualGenre.getCategoriesIDs().size() && expectedCategories.containsAll(actualGenre.getCategoriesIDs()));
        Assertions.assertNotNull(actualGenre.getCreatedAt());
        Assertions.assertNotNull(actualGenre.getUpdatedAt());
        Assertions.assertNull(actualGenre.getDeletedAt());

    }


    @Test
    public void asACatalogAdminIShouldBeAbleToCreateANewGenreWithCategories() throws Exception {
        Assertions.assertTrue(MYSQL_CONTAINER.isRunning());
        Assertions.assertEquals(0, genreRepository.count());

        final var filmes = givenACategory("Filmes", null, true);
        final var expectedName = "Ação";
        final var expectedIsActive = true;
        final var expectedCategories = List.of(filmes);

        final var actualId = givenAGenre(expectedName, expectedIsActive, expectedCategories);

        final var actualGenre = genreRepository.findById(actualId.getValue()).get();

        Assertions.assertEquals(expectedName, actualGenre.getName());
        Assertions.assertEquals(expectedIsActive, actualGenre.isActive());
        Assertions.assertTrue(expectedCategories.size() == actualGenre.getCategoriesIDs().size() && expectedCategories.containsAll(actualGenre.getCategoriesIDs()));
        Assertions.assertNotNull(actualGenre.getCreatedAt());
        Assertions.assertNotNull(actualGenre.getUpdatedAt());
        Assertions.assertNull(actualGenre.getDeletedAt());

    }



}
