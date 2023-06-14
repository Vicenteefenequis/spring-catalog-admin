package com.fullcycle.admin.catalog.e2e.genre;

import com.fullcycle.admin.catalog.ApiTest;
import com.fullcycle.admin.catalog.E2ETest;
import com.fullcycle.admin.catalog.domain.category.CategoryID;
import com.fullcycle.admin.catalog.domain.genre.GenreID;
import com.fullcycle.admin.catalog.e2e.MockDsl;
import com.fullcycle.admin.catalog.infrastructure.genre.models.UpdateGenreRequest;
import com.fullcycle.admin.catalog.infrastructure.genre.persistence.GenreRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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


    @Test
    public void asACatalogAdminIShouldBeAbleToNavigateThruAllGenres() throws Exception {
        Assertions.assertEquals(0, genreRepository.count());


        givenAGenre("Ação", true, List.of());
        givenAGenre("Esportes", true, List.of());
        givenAGenre("Drama", true, List.of());

        listGenres(0, 1).andExpect(status().isOk()).andExpect(jsonPath("$.current_page").value(0)).andExpect(jsonPath("$.per_page").value(1)).andExpect(jsonPath("$.total").value(3)).andExpect(jsonPath("$.items.length()").value(1)).andExpect(jsonPath("$.items[0].name").value("Ação"));


        listGenres(1, 1).andExpect(status().isOk()).andExpect(jsonPath("$.current_page").value(1)).andExpect(jsonPath("$.per_page").value(1)).andExpect(jsonPath("$.total").value(3)).andExpect(jsonPath("$.items.length()").value(1)).andExpect(jsonPath("$.items[0].name").value("Drama"));


        listGenres(2, 1).andExpect(status().isOk()).andExpect(jsonPath("$.current_page").value(2)).andExpect(jsonPath("$.per_page").value(1)).andExpect(jsonPath("$.total").value(3)).andExpect(jsonPath("$.items.length()").value(1)).andExpect(jsonPath("$.items[0].name").value("Esportes"));


        listGenres(3, 1).andExpect(status().isOk()).andExpect(jsonPath("$.current_page").value(3)).andExpect(jsonPath("$.per_page").value(1)).andExpect(jsonPath("$.total").value(3)).andExpect(jsonPath("$.items.length()").value(0));
    }


    @Test
    public void asACatalogAdminIShouldBeAbleToSearchBetweenAllGenres() throws Exception {
        Assertions.assertEquals(0, genreRepository.count());

        givenAGenre("Ação", true, List.of());
        givenAGenre("Esportes", true, List.of());
        givenAGenre("Drama", true, List.of());

        listGenres(0, 1, "dra").andExpect(status().isOk()).andExpect(jsonPath("$.current_page").value(0)).andExpect(jsonPath("$.per_page").value(1)).andExpect(jsonPath("$.total").value(1)).andExpect(jsonPath("$.items.length()").value(1)).andExpect(jsonPath("$.items[0].name").value("Drama"));
    }


    @Test
    public void asACatalogAdminIShouldBeAbleToSortAllGenresByNameDesc() throws Exception {
        Assertions.assertEquals(0, genreRepository.count());


        givenAGenre("Ação", true, List.of());
        givenAGenre("Esportes", true, List.of());
        givenAGenre("Drama", true, List.of());


        listGenres(0, 3, "", "name", "desc").andExpect(status().isOk()).andExpect(jsonPath("$.current_page").value(0)).andExpect(jsonPath("$.per_page").value(3)).andExpect(jsonPath("$.total").value(3)).andExpect(jsonPath("$.items.length()").value(3)).andExpect(jsonPath("$.items[0].name").value("Esportes")).andExpect(jsonPath("$.items[1].name").value("Drama")).andExpect(jsonPath("$.items[2].name").value("Ação"));
    }


    @Test
    public void asACatalogAdminIShouldBeAbleToGetAGenreByIdentifierWithValidValues() throws Exception {
        Assertions.assertEquals(0, genreRepository.count());

        final var filmes = givenACategory("Filmes", null, true);
        final var expectedName = "Ação";
        final var expectedIsActive = true;
        final var expectedCategories = List.of(filmes);

        final var actualId = givenAGenre(expectedName, expectedIsActive, expectedCategories);


        final var actualGenre = retrieveAGenre(actualId);

        Assertions.assertEquals(expectedName, actualGenre.name());
        Assertions.assertTrue(expectedCategories.size() == actualGenre.categories().size() && mapTo(expectedCategories, CategoryID::getValue).containsAll(actualGenre.categories()));
        Assertions.assertEquals(expectedIsActive, actualGenre.active());
        Assertions.assertNotNull(actualGenre.createdAt());
        Assertions.assertNotNull(actualGenre.updatedAt());
        Assertions.assertNull(actualGenre.deletedAt());
    }


    @Test
    public void asACatalogAdminIShouldBeAbleToSeeATreatedErrorByGettingANotFoundGenre() throws Exception {

        final var aRequest = get("/genres/123").with(ApiTest.ADMIN_JWT).contentType(MediaType.APPLICATION_JSON);

        this.mvc.perform(aRequest).andExpect(status().isNotFound()).andExpect(jsonPath("$.message").value("Genre with ID 123 was not found"));

    }

    @Test
    public void asACatalogAdminIShouldBeToUpdateAGenreByItsIdentifier() throws Exception {
        Assertions.assertEquals(0, genreRepository.count());

        final var filmes = givenACategory("Filmes", null, true);
        final var expectedName = "Ação";
        final var expectedIsActive = true;
        final var expectedCategories = List.of(filmes);

        final var actualId = givenAGenre("acao", expectedIsActive, expectedCategories);


        final var aRequestBody = new UpdateGenreRequest(expectedName, mapTo(expectedCategories, CategoryID::getValue), expectedIsActive);

        updateAGenre(actualId, aRequestBody).andExpect(status().isOk());


        final var actualGenre = genreRepository.findById(actualId.getValue()).get();


        Assertions.assertEquals(expectedName, actualGenre.getName());
        Assertions.assertTrue(
                expectedCategories.size() == actualGenre.getCategoriesIDs().size() &&
                        expectedCategories.containsAll(actualGenre.getCategoriesIDs())
        );
        Assertions.assertEquals(expectedIsActive, actualGenre.isActive());
        Assertions.assertNotNull(actualGenre.getCreatedAt());
        Assertions.assertNotNull(actualGenre.getUpdatedAt());
        Assertions.assertNull(actualGenre.getDeletedAt());
    }

    @Test
    public void asACatalogAdminIShouldBeToInactivateAGenreByItsIdentifier() throws Exception {
        Assertions.assertEquals(0, genreRepository.count());

        final var filmes = givenACategory("Filmes", null, true);
        final var expectedName = "Ação";
        final var expectedIsActive = false;
        final var expectedCategories = List.of(filmes);

        final var actualId = givenAGenre(expectedName, true, expectedCategories);


        final var aRequestBody = new UpdateGenreRequest(expectedName, mapTo(expectedCategories, CategoryID::getValue), expectedIsActive);

        updateAGenre(actualId, aRequestBody).andExpect(status().isOk());


        final var actualGenre = genreRepository.findById(actualId.getValue()).get();


        Assertions.assertEquals(expectedName, actualGenre.getName());
        Assertions.assertTrue(
                expectedCategories.size() == actualGenre.getCategoriesIDs().size() &&
                        expectedCategories.containsAll(actualGenre.getCategoriesIDs())
        );
        Assertions.assertEquals(expectedIsActive, actualGenre.isActive());
        Assertions.assertNotNull(actualGenre.getCreatedAt());
        Assertions.assertNotNull(actualGenre.getUpdatedAt());
        Assertions.assertNotNull(actualGenre.getDeletedAt());
    }


    @Test
    public void asACatalogAdminIShouldBeToActivateAGenreByItsIdentifier() throws Exception {
        Assertions.assertEquals(0, genreRepository.count());

        final var expectedName = "Ação";
        final var expectedIsActive = false;
        final var expectedCategories = List.<CategoryID>of();

        final var actualId = givenAGenre(expectedName, true, expectedCategories);


        final var aRequestBody = new UpdateGenreRequest(expectedName, mapTo(expectedCategories, CategoryID::getValue), expectedIsActive);

        updateAGenre(actualId, aRequestBody).andExpect(status().isOk());


        final var actualGenre = genreRepository.findById(actualId.getValue()).get();


        Assertions.assertEquals(expectedName, actualGenre.getName());
        Assertions.assertEquals(expectedCategories, actualGenre.getCategoriesIDs());
        Assertions.assertEquals(expectedIsActive, actualGenre.isActive());
        Assertions.assertNotNull(actualGenre.getCreatedAt());
        Assertions.assertNotNull(actualGenre.getUpdatedAt());
        Assertions.assertNotNull(actualGenre.getDeletedAt());
    }


    @Test
    public void asACatalogAdminIShouldBeAbleToDeleteAGenreByIdentifier() throws Exception {
        Assertions.assertEquals(0, genreRepository.count());

        final var filmes = givenACategory("Filmes", null, true);
        final var actualId = givenAGenre("Ação", true, List.of(filmes));

        deleteAGenre(actualId)
                .andExpect(status().isNoContent());

        Assertions.assertFalse(this.genreRepository.existsById(actualId.getValue()));
        Assertions.assertEquals(0, genreRepository.count());
    }

    @Test
    public void asACatalogAdminIShouldNotSeeAErrorByDeletingANotExistentGenre() throws Exception {
        Assertions.assertEquals(0, genreRepository.count());

        deleteAGenre(GenreID.from("123"))
                .andExpect(status().isNoContent());

        Assertions.assertEquals(0, genreRepository.count());
    }

}
