package com.fullcycle.admin.catalog.application.genre.update;

import com.fullcycle.admin.catalog.application.UseCaseTest;
import com.fullcycle.admin.catalog.domain.category.CategoryGateway;
import com.fullcycle.admin.catalog.domain.category.CategoryID;
import com.fullcycle.admin.catalog.domain.exceptions.NotificationException;
import com.fullcycle.admin.catalog.domain.genre.Genre;
import com.fullcycle.admin.catalog.domain.genre.GenreGateway;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class UpdateGenreUseCaseTest extends UseCaseTest {

    @InjectMocks
    private DefaultUpdateGenreUseCase useCase;


    @Mock
    private CategoryGateway categoryGateway;

    @Mock
    private GenreGateway genreGateway;

    @Override
    protected List<Object> getMocks() {
        return List.of(categoryGateway, genreGateway);
    }

    @Test
    public void givenAValidCommand_whenCallsUpdateGenre_shouldReturnGenreId() {
        //given
        final var aGenre = Genre.newGenre("Acao", true);
        final var expectedId = aGenre.getId();

        final var expectedName = "Ação";
        final var expectedIsActive = true;
        final var expectedCategories = List.<CategoryID>of();

        final var aCommand = UpdateGenreCommand.with(expectedId.getValue(), expectedName, expectedIsActive, asString(expectedCategories));

        when(genreGateway.findById(any()))
                .thenReturn(Optional.of(Genre.with(aGenre)));

        when(genreGateway.update(any()))
                .thenAnswer(returnsFirstArg());

        //when
        final var actualOutput = useCase.execute(aCommand);
        //then
        Assertions.assertNotNull(actualOutput);
        Assertions.assertEquals(expectedId.getValue(), actualOutput.id());


        verify(genreGateway, times(1)).findById(eq(expectedId));
        verify(genreGateway, times(1)).update(argThat(aUpdateGenre ->
                Objects.equals(expectedId, aUpdateGenre.getId())
                        && Objects.equals(expectedName, aUpdateGenre.getName())
                        && Objects.equals(expectedIsActive, aUpdateGenre.isActive())
                        && Objects.equals(expectedCategories, aUpdateGenre.getCategories())
                        && Objects.equals(aGenre.getCreatedAt(), aUpdateGenre.getCreatedAt())
                        && aGenre.getUpdatedAt().isBefore(aUpdateGenre.getUpdatedAt())
                        && Objects.isNull(aUpdateGenre.getDeletedAt())

        ));
    }


    @Test
    public void givenAValidCommandWithInactiveGenre_whenCallsUpdateGenre_shouldReturnGenreId() {
        //given
        final var aGenre = Genre.newGenre("Acao", true);
        final var expectedId = aGenre.getId();

        final var expectedName = "Ação";
        final var expectedIsActive = false;
        final var expectedCategories = List.<CategoryID>of();

        final var aCommand = UpdateGenreCommand.with(expectedId.getValue(), expectedName, expectedIsActive, asString(expectedCategories));

        when(genreGateway.findById(any()))
                .thenReturn(Optional.of(Genre.with(aGenre)));

        when(genreGateway.update(any()))
                .thenAnswer(returnsFirstArg());

        Assertions.assertNull(aGenre.getDeletedAt());

        //when
        final var actualOutput = useCase.execute(aCommand);
        //then
        Assertions.assertNotNull(actualOutput);
        Assertions.assertEquals(expectedId.getValue(), actualOutput.id());


        verify(genreGateway, times(1)).findById(eq(expectedId));
        verify(genreGateway, times(1)).update(argThat(aUpdateGenre ->
                Objects.equals(expectedId, aUpdateGenre.getId())
                        && Objects.equals(expectedName, aUpdateGenre.getName())
                        && Objects.equals(expectedIsActive, aUpdateGenre.isActive())
                        && Objects.equals(expectedCategories, aUpdateGenre.getCategories())
                        && Objects.equals(aGenre.getCreatedAt(), aUpdateGenre.getCreatedAt())
                        && aGenre.getUpdatedAt().isBefore(aUpdateGenre.getUpdatedAt())
                        && Objects.nonNull(aUpdateGenre.getDeletedAt())

        ));
    }


    @Test
    public void givenAValidCommandWithCategories_whenCallsUpdateGenre_shouldReturnGenreId() {
        //given
        final var aGenre = Genre.newGenre("Acao", true);
        final var expectedId = aGenre.getId();

        final var expectedName = "Ação";
        final var expectedIsActive = true;
        final var expectedCategories = List.of(
                CategoryID.from("123"),
                CategoryID.from("456")
        );

        final var aCommand = UpdateGenreCommand.with(expectedId.getValue(), expectedName, expectedIsActive, asString(expectedCategories));

        when(genreGateway.findById(any()))
                .thenReturn(Optional.of(Genre.with(aGenre)));

        when(categoryGateway.existsById(any()))
                .thenReturn(expectedCategories);

        when(genreGateway.update(any()))
                .thenAnswer(returnsFirstArg());

        //when
        final var actualOutput = useCase.execute(aCommand);
        //then
        Assertions.assertNotNull(actualOutput);
        Assertions.assertEquals(expectedId.getValue(), actualOutput.id());


        verify(genreGateway, times(1)).findById(eq(expectedId));

        verify(categoryGateway, times(1)).existsById(eq(expectedCategories));

        verify(genreGateway, times(1)).update(argThat(aUpdateGenre ->
                Objects.equals(expectedId, aUpdateGenre.getId())
                        && Objects.equals(expectedName, aUpdateGenre.getName())
                        && Objects.equals(expectedIsActive, aUpdateGenre.isActive())
                        && Objects.equals(expectedCategories, aUpdateGenre.getCategories())
                        && Objects.equals(aGenre.getCreatedAt(), aUpdateGenre.getCreatedAt())
                        && aGenre.getUpdatedAt().isBefore(aUpdateGenre.getUpdatedAt())
                        && Objects.isNull(aUpdateGenre.getDeletedAt())

        ));
    }


    @Test
    public void givenAnInvalidName_whenCallsUpdateGenre_shouldReturnNotificationException() {
        //given
        final var aGenre = Genre.newGenre("Acao", true);
        final var expectedId = aGenre.getId();

        final String expectedName = null;
        final var expectedIsActive = true;
        final var expectedCategories = List.<CategoryID>of();

        final var expectedErrorCount = 1;
        final var expectedErrorMessage = "'name' should not be null";


        final var aCommand = UpdateGenreCommand.with(expectedId.getValue(), expectedName, expectedIsActive, asString(expectedCategories));

        when(genreGateway.findById(any()))
                .thenReturn(Optional.of(Genre.with(aGenre)));


        //when
        final var actualOutput = Assertions.assertThrows(NotificationException.class, () -> useCase.execute(aCommand));
        //then

        Assertions.assertEquals(expectedErrorCount, actualOutput.getErrors().size());
        Assertions.assertEquals(expectedErrorMessage, actualOutput.getErrors().get(0).message());

        verify(genreGateway, times(1)).findById(eq(expectedId));
    }


    @Test
    public void givenAnInvalidName_whenCallsUpdateGenreAndSomeCategoriesDoesNotExists_shouldReturnNotificationException() {
        //given
        final var filmes = CategoryID.from("123");
        final var series = CategoryID.from("456");
        final var documentarios = CategoryID.from("789");

        final var aGenre = Genre.newGenre("Acao", true);
        final var expectedId = aGenre.getId();

        final String expectedName = null;
        final var expectedIsActive = true;

        final var expectedCategories = List.of(
                filmes,
                series,
                documentarios
        );

        final var expectedErrorCount = 2;
        final var expectedErrorMessageOne = "Some categories could not be found: 456, 789";
        final var expectedErrorMessageTwo = "'name' should not be null";


        final var aCommand = UpdateGenreCommand.with(expectedId.getValue(), expectedName, expectedIsActive, asString(expectedCategories));

        when(genreGateway.findById(any()))
                .thenReturn(Optional.of(Genre.with(aGenre)));

        when(categoryGateway.existsById(any()))
                .thenReturn(List.of(filmes));


        //when
        final var actualOutput = Assertions.assertThrows(NotificationException.class, () -> useCase.execute(aCommand));
        //then

        Assertions.assertEquals(expectedErrorCount, actualOutput.getErrors().size());
        Assertions.assertEquals(expectedErrorMessageOne, actualOutput.getErrors().get(0).message());
        Assertions.assertEquals(expectedErrorMessageTwo, actualOutput.getErrors().get(1).message());

        verify(genreGateway, times(1)).findById(eq(expectedId));
        verify(categoryGateway, times(1)).existsById(eq(expectedCategories));
    }


    private List<String> asString(final List<CategoryID> ids) {
        return ids.stream().map(CategoryID::getValue).toList();
    }


}