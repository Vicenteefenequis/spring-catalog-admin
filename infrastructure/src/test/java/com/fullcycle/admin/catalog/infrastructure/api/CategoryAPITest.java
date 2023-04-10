package com.fullcycle.admin.catalog.infrastructure.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fullcycle.admin.catalog.ControllerTest;
import com.fullcycle.admin.catalog.application.category.create.CreateCategoryOutput;
import com.fullcycle.admin.catalog.application.category.create.CreateCategoryUseCase;
import com.fullcycle.admin.catalog.application.category.retrieve.get.CategoryOutput;
import com.fullcycle.admin.catalog.application.category.retrieve.get.GetCategoryByIdUseCase;
import com.fullcycle.admin.catalog.application.category.update.UpdateCategoryUseCase;
import com.fullcycle.admin.catalog.domain.category.Category;
import com.fullcycle.admin.catalog.domain.category.CategoryID;
import com.fullcycle.admin.catalog.domain.exceptions.DomainException;
import com.fullcycle.admin.catalog.domain.exceptions.NotFoundException;
import com.fullcycle.admin.catalog.domain.validation.Error;
import com.fullcycle.admin.catalog.domain.validation.handler.Notification;
import com.fullcycle.admin.catalog.infrastructure.category.models.CreateCategoryApiInput;
import com.fullcycle.admin.catalog.infrastructure.category.models.UpdateCategoryApiInput;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Objects;

import static io.vavr.API.Left;
import static io.vavr.API.Right;
import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ControllerTest(controllers = CategoryAPI.class)
public class CategoryAPITest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper mapper;


    @MockBean
    private CreateCategoryUseCase createCategoryUseCase;

    @MockBean
    private GetCategoryByIdUseCase getCategoryByIdUseCase;

    @MockBean
    private UpdateCategoryUseCase updateCategoryUseCase;

    @Test
    public void givenAValidCommand_whenCallsCreateCategory_shouldReturnCategoryId() throws Exception {
        final var expectedName = "Filmes";
        final var expectedDescription = "A categoria mais assistida";
        final var expectedIsActive = true;


        final var aInput =
                new CreateCategoryApiInput(expectedName, expectedDescription, expectedIsActive);

        when(createCategoryUseCase.execute(any()))
                .thenReturn(Right(CreateCategoryOutput.from("123")));

        final var request = post("/categories")
                .contentType(MediaType.APPLICATION_JSON)
                .content(this.mapper.writeValueAsString(aInput));

        this.mvc.perform(request)
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", "/categories/123"))
                .andExpect(header().string("Content-Type", MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.id").value(equalTo("123")));


        Mockito.verify(createCategoryUseCase, times(1)).execute(argThat(cmd ->
                Objects.equals(expectedName, cmd.name()) &&
                        Objects.equals(expectedDescription, cmd.description()) &&
                        Objects.equals(expectedIsActive, cmd.isActive())
        ));

    }


    @Test
    public void givenAInvalidName_whenCallsCreateCategory_thenShouldReturnNotification() throws Exception {
        final String expectedName = null;
        final var expectedDescription = "A categoria mais assistida";
        final var expectedMessage = "'name' should not be null";
        final var expectedIsActive = true;


        final var aInput =
                new CreateCategoryApiInput(expectedName, expectedDescription, expectedIsActive);

        when(createCategoryUseCase.execute(any()))
                .thenReturn(Left(Notification.create(new Error(expectedMessage))));

        final var request = post("/categories")
                .contentType(MediaType.APPLICATION_JSON)
                .content(this.mapper.writeValueAsString(aInput));

        this.mvc.perform(request)
                .andDo(print())
                .andExpect(status().isUnprocessableEntity())
                .andExpect(header().string("Location", nullValue()))
                .andExpect(header().string("Content-Type", MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.errors").value(hasSize(1)))
                .andExpect(jsonPath("$.errors[0].message").value(equalTo(expectedMessage)));


        Mockito.verify(createCategoryUseCase, times(1)).execute(argThat(cmd ->
                Objects.equals(expectedName, cmd.name()) &&
                        Objects.equals(expectedDescription, cmd.description()) &&
                        Objects.equals(expectedIsActive, cmd.isActive())
        ));

    }


    @Test
    public void givenAInvalidCommand_whenCallsCreateCategory_thenShouldReturnDomainException() throws Exception {
        final String expectedName = null;
        final var expectedDescription = "A categoria mais assistida";
        final var expectedMessage = "'name' should not be null";
        final var expectedIsActive = true;


        final var aInput =
                new CreateCategoryApiInput(expectedName, expectedDescription, expectedIsActive);

        when(createCategoryUseCase.execute(any()))
                .thenThrow(DomainException.with(new Error(expectedMessage)));

        final var request = post("/categories")
                .contentType(MediaType.APPLICATION_JSON)
                .content(this.mapper.writeValueAsString(aInput));

        this.mvc.perform(request)
                .andDo(print())
                .andExpect(status().isUnprocessableEntity())
                .andExpect(header().string("Location", nullValue()))
                .andExpect(header().string("Content-Type", MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.message").value(equalTo(expectedMessage)))
                .andExpect(jsonPath("$.errors").value(hasSize(1)))
                .andExpect(jsonPath("$.errors[0].message").value(equalTo(expectedMessage)));


        Mockito.verify(createCategoryUseCase, times(1)).execute(argThat(cmd ->
                Objects.equals(expectedName, cmd.name()) &&
                        Objects.equals(expectedDescription, cmd.description()) &&
                        Objects.equals(expectedIsActive, cmd.isActive())
        ));

    }


    @Test
    public void givenAValidId_whenCallsGetCategory_shouldReturnCategory() throws Exception {
        //given
        final var expectedName = "filmes";
        final var expectedDescription = "A categoria mais assistida";
        final var expectedIsActive = true;

        final var aCategory =
                Category.newCategory(expectedName, expectedDescription, expectedIsActive);

        final var expectedId = aCategory.getId().getValue();

        when(getCategoryByIdUseCase.execute(any()))
                .thenReturn(CategoryOutput.from(aCategory));


        // when
        final var request = get("/categories/{id}", expectedId)
                .contentType(MediaType.APPLICATION_JSON);

        final var response = this.mvc.perform(request).andDo(print());

        //then

        response.andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(equalTo(expectedId)))
                .andExpect(jsonPath("$.name").value(equalTo(expectedName)))
                .andExpect(jsonPath("$.description").value(equalTo(expectedDescription)))
                .andExpect(jsonPath("$.is_active").value(equalTo(expectedIsActive)))
                .andExpect(jsonPath("$.created_at").value(equalTo(aCategory.getCreatedAt().toString())))
                .andExpect(jsonPath("$.updated_at").value(equalTo(aCategory.getUpdatedAt().toString())))
                .andExpect(jsonPath("$.deleted_at").value(equalTo(aCategory.getDeletedAt())));

        verify(getCategoryByIdUseCase, times(1)).execute(eq(expectedId));
    }


    @Test
    public void givenAInvalidId_whenCallsGetCategory_shouldReturnNotFound() throws Exception {
        //given
        final var expectedErrorMessage = "Category with ID 123 was not found";
        final var expectedId = CategoryID.from("123");
        when(getCategoryByIdUseCase.execute(any()))
                .thenThrow(NotFoundException.with(Category.class, expectedId));

        //when
        final var request = get("/categories/{id}", expectedId.getValue())
                .contentType(MediaType.APPLICATION_JSON);
        final var response = this.mvc.perform(request).andDo(print());

        //then
        response.andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value(equalTo(expectedErrorMessage)));


    }


    @Test
    public void givenAValidCommand_whenCallsUpdateCategory_shouldReturnCategoryId() throws Exception {
        //given
        final var expectedId = "123";
        final var expectedName = "filmes";
        final var expectedDescription = "A categoria mais assistida";
        final var expectedIsActive = true;

        when(getCategoryByIdUseCase.execute(any()))
                .thenThrow(NotFoundException.with(Category.class, CategoryID.from(expectedId)));


        final var aCommand = new UpdateCategoryApiInput(expectedName, expectedDescription, expectedIsActive);

        // when
        final var request = put("/categories/{id}", expectedId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(aCommand));

        final var response = this.mvc.perform(request).andDo(print());

        //then

        response.andExpect(status().isOk())
                .andExpect(header().string("Content-Type", MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.id").value(equalTo(expectedId)));


        verify(updateCategoryUseCase, times(1)).execute(argThat(cmd ->
                Objects.equals(expectedId, cmd.id()) &&
                        Objects.equals(expectedName, cmd.name()) &&
                        Objects.equals(expectedDescription, cmd.description()) &&
                        Objects.equals(expectedIsActive, cmd.isActive())
        ));
    }


    @Test
    public void givenAInvalidId_whenCallsUpdateCategory_shouldReturnNotFound() throws Exception {
        //given
        final var expectedId = "not-found";
        final var expectedName = "filmes";
        final var expectedDescription = "A categoria mais assistida";
        final var expectedIsActive = true;

        final var expectedErrorMessage = "Category with ID not-found was not found";


        when(updateCategoryUseCase.execute(any()))
                .thenThrow(NotFoundException.with(Category.class, CategoryID.from(expectedId)));


        final var aCommand = new UpdateCategoryApiInput(expectedName, expectedDescription, expectedIsActive);

        // when
        final var request = put("/categories/{id}", expectedId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(aCommand));

        final var response = this.mvc.perform(request).andDo(print());

        //then

        response.andExpect(status().isNotFound())
                .andExpect(header().string("Content-Type", MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.message").value(equalTo(expectedErrorMessage)));


        verify(updateCategoryUseCase, times(1)).execute(argThat(cmd ->
                Objects.equals(expectedName, cmd.name()) &&
                        Objects.equals(expectedDescription, cmd.description()) &&
                        Objects.equals(expectedIsActive, cmd.isActive())
        ));
    }


}
