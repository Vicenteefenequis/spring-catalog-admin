package com.fullcycle.admin.catalog.e2e.category;

import com.fullcycle.admin.catalog.E2ETest;
import com.fullcycle.admin.catalog.e2e.MockDsl;
import com.fullcycle.admin.catalog.infrastructure.category.models.CategoryResponse;
import com.fullcycle.admin.catalog.infrastructure.category.models.UpdateCategoryRequest;
import com.fullcycle.admin.catalog.infrastructure.category.persistence.CategoryRepository;
import com.fullcycle.admin.catalog.infrastructure.configuration.json.Json;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@E2ETest
@Testcontainers
public class CategoryE2ETest implements MockDsl {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private CategoryRepository categoryRepository;

    @Container
    public static final MySQLContainer MYSQL_CONTAINER = new MySQLContainer("mysql:latest").withPassword("root").withUsername("root").withDatabaseName("admin_video");


    @DynamicPropertySource
    public static void setDatasourceProperties(final DynamicPropertyRegistry registry) {
        registry.add("mysql.port", () -> MYSQL_CONTAINER.getMappedPort(3306));
    }

    @Override
    public MockMvc mvc() {
        return this.mvc;
    }

    @Test
    public void asACatalogAdminIShouldBeAbleToCreateANewCategoryWithValidValues() throws Exception {
        Assertions.assertEquals(0, categoryRepository.count());

        final var expectedName = "Filmes";
        final var expectedDescription = "A categoria mais assistida";
        final var expectedIsActive = true;


        final var actualId = givenACategory(expectedName, expectedDescription, expectedIsActive);

        final var actualCategory = retrieveCategory(actualId.getValue());

        Assertions.assertEquals(expectedName, actualCategory.name());
        Assertions.assertEquals(expectedDescription, actualCategory.description());
        Assertions.assertEquals(expectedIsActive, actualCategory.active());
        Assertions.assertNotNull(actualCategory.createdAt());
        Assertions.assertNotNull(actualCategory.updatedAt());
        Assertions.assertNull(actualCategory.deletedAt());

    }

    @Test
    public void asACatalogAdminIShouldBeAbleToNavigateToPageAllCategories() throws Exception {
        Assertions.assertEquals(0, categoryRepository.count());


        givenACategory("Filmes", null, true);
        givenACategory("Documentarios", null, true);
        givenACategory("Series", null, true);

        listCategories(0, 1)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.current_page").value(0))
                .andExpect(jsonPath("$.per_page").value(1))
                .andExpect(jsonPath("$.total").value(3))
                .andExpect(jsonPath("$.items.length()").value(1))
                .andExpect(jsonPath("$.items[0].name").value("Documentarios"));


        listCategories(1, 1)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.current_page").value(1))
                .andExpect(jsonPath("$.per_page").value(1))
                .andExpect(jsonPath("$.total").value(3))
                .andExpect(jsonPath("$.items.length()").value(1))
                .andExpect(jsonPath("$.items[0].name").value("Filmes"));


        listCategories(2, 1)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.current_page").value(2))
                .andExpect(jsonPath("$.per_page").value(1))
                .andExpect(jsonPath("$.total").value(3))
                .andExpect(jsonPath("$.items.length()").value(1))
                .andExpect(jsonPath("$.items[0].name").value("Series"));


        listCategories(3, 1)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.current_page").value(3))
                .andExpect(jsonPath("$.per_page").value(1))
                .andExpect(jsonPath("$.total").value(3))
                .andExpect(jsonPath("$.items.length()").value(0));
    }


    @Test
    public void asACatalogAdminIShouldBeAbleToSearchBetweenAllCategories() throws Exception {
        Assertions.assertEquals(0, categoryRepository.count());


        givenACategory("Filmes", null, true);
        givenACategory("Documentarios", null, true);
        givenACategory("Series", null, true);

        listCategories(0, 1, "fil")
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.current_page").value(0))
                .andExpect(jsonPath("$.per_page").value(1))
                .andExpect(jsonPath("$.total").value(1))
                .andExpect(jsonPath("$.items.length()").value(1))
                .andExpect(jsonPath("$.items[0].name").value("Filmes"));
    }


    @Test
    public void asACatalogAdminIShouldBeAbleToSortAllCategoriesByDescriptionDesc() throws Exception {
        Assertions.assertEquals(0, categoryRepository.count());


        givenACategory("Filmes", "C", true);
        givenACategory("Documentarios", "Z", true);
        givenACategory("Series", "A", true);

        listCategories(0, 3, "", "description", "desc")
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.current_page").value(0))
                .andExpect(jsonPath("$.per_page").value(3))
                .andExpect(jsonPath("$.total").value(3))
                .andExpect(jsonPath("$.items.length()").value(3))
                .andExpect(jsonPath("$.items[0].name").value("Documentarios"))
                .andExpect(jsonPath("$.items[1].name").value("Filmes"))
                .andExpect(jsonPath("$.items[2].name").value("Series"));
    }


    @Test
    public void asACatalogAdminIShouldBeAbleToGetACategoryByIdentifierWithValidValues() throws Exception {
        Assertions.assertEquals(0, categoryRepository.count());

        final var expectedName = "Filmes";
        final var expectedDescription = "A categoria mais assistida";
        final var expectedIsActive = true;


        final var actualId = givenACategory(expectedName, expectedDescription, expectedIsActive);

        final var actualCategory = retrieveCategory(actualId.getValue());

        Assertions.assertEquals(expectedName, actualCategory.name());
        Assertions.assertEquals(expectedDescription, actualCategory.description());
        Assertions.assertEquals(expectedIsActive, actualCategory.active());
        Assertions.assertNotNull(actualCategory.createdAt());
        Assertions.assertNotNull(actualCategory.updatedAt());
        Assertions.assertNull(actualCategory.deletedAt());
    }


    @Test
    public void asACatalogAdminIShouldBeAbleToSeeATreatedErrorByGettingANotFoundCategory() throws Exception {

        final var aRequest = get("/categories/123").contentType(MediaType.APPLICATION_JSON);

        this.mvc.perform(aRequest).andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Category with ID 123 was not found"));

    }


    @Test
    public void asACatalogAdminIShouldBeToUpdateACategoryByItsIdentifier() throws Exception {
        Assertions.assertEquals(0, categoryRepository.count());


        final var actualId = givenACategory("Movies", null, true);

        final var expectedName = "Filmes";
        final var expectedDescription = "A categoria mais assistida";
        final var expectedIsActive = true;


        final var aRequestBody = new UpdateCategoryRequest(expectedName, expectedDescription, expectedIsActive);

        final var aRequest = put("/categories/" + actualId.getValue())
                .contentType(MediaType.APPLICATION_JSON).content(Json.writeValueAsString(aRequestBody));

        this.mvc.perform(aRequest)
                .andExpect(status().isOk());


        final var actualCategory = categoryRepository.findById(actualId.getValue()).get();


        Assertions.assertEquals(expectedName, actualCategory.getName());
        Assertions.assertEquals(expectedDescription, actualCategory.getDescription());
        Assertions.assertEquals(expectedIsActive, actualCategory.isActive());
        Assertions.assertNotNull(actualCategory.getCreatedAt());
        Assertions.assertNotNull(actualCategory.getUpdatedAt());
        Assertions.assertNull(actualCategory.getDeletedAt());
    }

    @Test
    public void asACatalogAdminIShouldBeToInactivateACategoryByItsIdentifier() throws Exception {
        Assertions.assertEquals(0, categoryRepository.count());

        final var expectedName = "Filmes";
        final var expectedDescription = "A categoria mais assistida";
        final var expectedIsActive = false;

        final var actualId = givenACategory(expectedName, expectedDescription, true);


        final var aRequestBody = new UpdateCategoryRequest(expectedName, expectedDescription, expectedIsActive);

        final var aRequest = put("/categories/" + actualId.getValue())
                .contentType(MediaType.APPLICATION_JSON).content(Json.writeValueAsString(aRequestBody));

        this.mvc.perform(aRequest)
                .andExpect(status().isOk());


        final var actualCategory = categoryRepository.findById(actualId.getValue()).get();


        Assertions.assertEquals(expectedName, actualCategory.getName());
        Assertions.assertEquals(expectedDescription, actualCategory.getDescription());
        Assertions.assertEquals(expectedIsActive, actualCategory.isActive());
        Assertions.assertNotNull(actualCategory.getCreatedAt());
        Assertions.assertNotNull(actualCategory.getUpdatedAt());
        Assertions.assertNotNull(actualCategory.getDeletedAt());
    }


    @Test
    public void asACatalogAdminIShouldBeToActivateACategoryByItsIdentifier() throws Exception {
        Assertions.assertEquals(0, categoryRepository.count());

        final var expectedName = "Filmes";
        final var expectedDescription = "A categoria mais assistida";
        final var expectedIsActive = true;

        final var actualId = givenACategory(expectedName, expectedDescription, false);


        final var aRequestBody = new UpdateCategoryRequest(expectedName, expectedDescription, expectedIsActive);

        final var aRequest = put("/categories/" + actualId.getValue())
                .contentType(MediaType.APPLICATION_JSON).content(Json.writeValueAsString(aRequestBody));

        this.mvc.perform(aRequest)
                .andExpect(status().isOk());


        final var actualCategory = categoryRepository.findById(actualId.getValue()).get();


        Assertions.assertEquals(expectedName, actualCategory.getName());
        Assertions.assertEquals(expectedDescription, actualCategory.getDescription());
        Assertions.assertEquals(expectedIsActive, actualCategory.isActive());
        Assertions.assertNotNull(actualCategory.getCreatedAt());
        Assertions.assertNotNull(actualCategory.getUpdatedAt());
        Assertions.assertNull(actualCategory.getDeletedAt());
    }


    @Test
    public void asACatalogAdminIShouldBeAbleToDeleteACategoryByIdentifier() throws Exception {
        Assertions.assertEquals(0, categoryRepository.count());

        final var actualId = givenACategory("Filmes", null, true);

        this.mvc.perform(delete("/categories/" + actualId.getValue())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        Assertions.assertFalse(this.categoryRepository.existsById(actualId.getValue()));

    }


    private ResultActions listCategories(final int page, final int perPage, final String search) throws Exception {
        return listCategories(page, perPage, search, "", "");
    }

    private ResultActions listCategories(final int page, final int perPage) throws Exception {
        return listCategories(page, perPage, "", "", "");
    }

    private ResultActions listCategories(final int page, final int perPage, final String search, final String sort, final String direction) throws Exception {
        final var aRequest = get("/categories/").queryParam("page", String.valueOf(page)).queryParam("perPage", String.valueOf(perPage)).queryParam("search", search).queryParam("sort", sort).queryParam("dir", direction).contentType(MediaType.APPLICATION_JSON);
        return this.mvc.perform(aRequest);
    }




    private CategoryResponse retrieveCategory(final String aCategoryId) throws Exception {
        final var aRequest = get("/categories/" + aCategoryId).contentType(MediaType.APPLICATION_JSON);

        final var actualResponse = this.mvc.perform(aRequest).andExpect(status().isOk()).andReturn().getResponse().getContentAsString();

        return Json.readValue(actualResponse, CategoryResponse.class);
    }
}
