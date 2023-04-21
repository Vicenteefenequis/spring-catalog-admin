package com.fullcycle.admin.catalog.application.genre.create;

import com.fullcycle.admin.catalog.IntegrationTest;
import com.fullcycle.admin.catalog.domain.category.Category;
import com.fullcycle.admin.catalog.domain.category.CategoryGateway;
import com.fullcycle.admin.catalog.domain.category.CategoryID;
import com.fullcycle.admin.catalog.domain.exceptions.NotificationException;
import com.fullcycle.admin.catalog.domain.genre.GenreGateway;
import com.fullcycle.admin.catalog.infrastructure.genre.persistence.GenreRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.SpyBean;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@IntegrationTest
public class CreateGenreUseCaseIT {
    @Autowired
    private CreateGenreUseCase useCase;
    @SpyBean
    private CategoryGateway categoryGateway;
    @SpyBean
    private GenreGateway genreGateway;
    @Autowired
    private GenreRepository genreRepository;

    @Test
    public void givenAValidCommand_whenCallsCreateGenre_shouldReturnGenreId() {
        //given
        final var filmes = categoryGateway.create(Category.newCategory("Ação", null, true));
        final var expectedName = "Ação";
        final var expectedIsActive = true;
        final var expectedCategories = List.of(filmes.getId());


        final var aCommand = CreateGenreCommand.with(expectedName, expectedIsActive, asString(expectedCategories));


        // when
        final var actualOutput = useCase.execute(aCommand);

        //then
        Assertions.assertNotNull(actualOutput);
        Assertions.assertNotNull(actualOutput.id());

        genreRepository.findById(actualOutput.id()).ifPresent(genre -> {
            Assertions.assertEquals(expectedName, genre.getName());
            Assertions.assertEquals(expectedIsActive, genre.isActive());
            Assertions.assertTrue(expectedCategories.size() == genre.getCategories().size() && expectedCategories.containsAll(genre.getCategoriesIDs()));
            Assertions.assertNotNull(genre.getId());
            Assertions.assertNotNull(genre.getCreatedAt());
            Assertions.assertNotNull(genre.getUpdatedAt());
            Assertions.assertNull(genre.getDeletedAt());
        });
    }


    @Test
    public void givenAValidCommandWithoutCategories_whenCallsCreateGenre_shouldReturnGenreId() {
        //given
        final var expectedName = "Ação";
        final var expectedIsActive = true;
        final var expectedCategories = List.<CategoryID>of();


        final var aCommand = CreateGenreCommand.with(expectedName, expectedIsActive, asString(expectedCategories));


        // when
        final var actualOutput = useCase.execute(aCommand);

        //then
        Assertions.assertNotNull(actualOutput);
        Assertions.assertNotNull(actualOutput.id());

        genreRepository.findById(actualOutput.id()).ifPresent(genre -> {
            Assertions.assertEquals(expectedName, genre.getName());
            Assertions.assertEquals(expectedIsActive, genre.isActive());
            Assertions.assertTrue(expectedCategories.size() == genre.getCategories().size() && expectedCategories.containsAll(genre.getCategoriesIDs()));
            Assertions.assertNotNull(genre.getId());
            Assertions.assertNotNull(genre.getCreatedAt());
            Assertions.assertNotNull(genre.getUpdatedAt());
            Assertions.assertNull(genre.getDeletedAt());
        });
    }


    @Test
    public void givenAValidCommandWithInactiveGenre_whenCallsCreateGenre_shouldReturnGenreId() {
        //given
        final var expectedName = "Ação";
        final var expectedIsActive = false;
        final var expectedCategories = List.<CategoryID>of();


        final var aCommand = CreateGenreCommand.with(expectedName, expectedIsActive, asString(expectedCategories));

        // when
        final var actualOutput = useCase.execute(aCommand);

        //then
        Assertions.assertNotNull(actualOutput);
        Assertions.assertNotNull(actualOutput.id());

        genreRepository.findById(actualOutput.id()).ifPresent(genre -> {
            Assertions.assertEquals(expectedName, genre.getName());
            Assertions.assertEquals(expectedIsActive, genre.isActive());
            Assertions.assertTrue(expectedCategories.size() == genre.getCategories().size() && expectedCategories.containsAll(genre.getCategoriesIDs()));
            Assertions.assertNotNull(genre.getId());
            Assertions.assertNotNull(genre.getCreatedAt());
            Assertions.assertNotNull(genre.getUpdatedAt());
            Assertions.assertNotNull(genre.getDeletedAt());
        });
    }


    @Test
    public void givenAInvalidEmptyName_whenCallsCreateGenre_shouldReturnDomainException() {
        //given
        final var expectedName = "";
        final var expectedIsActive = true;
        final var expectedCategories = List.<CategoryID>of();

        final var expectedErrorMessage = "'name' should not be empty";
        final var expectedErrorCount = 1;

        final var aCommand = CreateGenreCommand.with(expectedName, expectedIsActive, asString(expectedCategories));


        // when
        final var actualException = Assertions.assertThrows(NotificationException.class, () -> useCase.execute(aCommand));

        //then
        Assertions.assertEquals(expectedErrorCount, actualException.getErrors().size());
        Assertions.assertEquals(expectedErrorMessage, actualException.getErrors().get(0).message());

        verify(categoryGateway, times(0)).existsById(any());
        verify(genreGateway, times(0)).create(any());

    }


    @Test
    public void givenAInvalidName_whenCallsCreateGenreAndSomeCategoriesDoesNotExists_shouldReturnDomainException() {
        //given
        final var series = categoryGateway.create(Category.newCategory("Ação", null, true));
        final var filmes = CategoryID.from("456");
        final var documentarios = CategoryID.from("789");

        final var expectedName = " ";
        final var expectedIsActive = true;
        final var expectedCategories = List.of(filmes, series.getId(), documentarios);

        final var expectedErrorMessageOne = "Some categories could not be found: 456, 789";
        final var expectedErrorMessageTwo = "'name' should not be empty";
        final var expectedErrorCount = 2;


        final var aCommand = CreateGenreCommand.with(expectedName, expectedIsActive, asString(expectedCategories));


        // when
        final var actualException = Assertions.assertThrows(NotificationException.class, () -> useCase.execute(aCommand));

        //then
        Assertions.assertEquals(expectedErrorCount, actualException.getErrors().size());
        Assertions.assertEquals(expectedErrorMessageOne, actualException.getErrors().get(0).message());
        Assertions.assertEquals(expectedErrorMessageTwo, actualException.getErrors().get(1).message());

        verify(categoryGateway, times(1)).existsById(any());
        verify(genreGateway, times(0)).create(any());

    }


    private List<String> asString(List<CategoryID> categories) {
        return categories.stream().map(CategoryID::getValue).toList();
    }
}
