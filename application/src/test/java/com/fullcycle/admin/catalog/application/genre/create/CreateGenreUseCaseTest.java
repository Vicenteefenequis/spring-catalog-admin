package com.fullcycle.admin.catalog.application.genre.create;


import com.fullcycle.admin.catalog.domain.category.CategoryGateway;
import com.fullcycle.admin.catalog.domain.category.CategoryID;
import com.fullcycle.admin.catalog.domain.exceptions.NotificationException;
import com.fullcycle.admin.catalog.domain.genre.GenreGateway;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Objects;

import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CreateGenreUseCaseTest {

    @InjectMocks
    private DefaultCreateGenreUseCase useCase;

    @Mock
    private CategoryGateway categoryGateway;
    @Mock
    private GenreGateway genreGateway;

    @Test
    public void givenAValidCommand_whenCallsCreateGenre_shouldReturnGenreId() {
        //given
        final var expectedName = "Ação";
        final var expectedIsActive = true;
        final var expectedCategories = List.<CategoryID>of();


        final var aCommand = CreateGenreCommand.with(expectedName, expectedIsActive, asString(expectedCategories));

        when(genreGateway.create(any()))
                .thenAnswer(returnsFirstArg());

        // when
        final var actualOutput = useCase.execute(aCommand);

        //then
        Assertions.assertNotNull(actualOutput);
        Assertions.assertNotNull(actualOutput.id());

        verify(genreGateway, times(1)).create(argThat(genre ->
                Objects.equals(expectedName, genre.getName()) &&
                        Objects.equals(expectedIsActive, genre.isActive()) &&
                        Objects.equals(expectedCategories, genre.getCategories()) &&
                        Objects.nonNull(genre.getId()) &&
                        Objects.nonNull(genre.getCreatedAt()) &&
                        Objects.nonNull(genre.getUpdatedAt()) &&
                        Objects.isNull(genre.getDeletedAt())
        ));
    }

    @Test
    public void givenAValidCommandWithCategories_whenCallsCreateGenre_shouldReturnGenreId() {
        //given
        final var expectedName = "Ação";
        final var expectedIsActive = true;
        final var expectedCategories = List.of(
                CategoryID.from("123"),
                CategoryID.from("456")
        );


        final var aCommand = CreateGenreCommand.with(expectedName, expectedIsActive, asString(expectedCategories));

        when(categoryGateway.existsById(any()))
                .thenReturn(expectedCategories);

        when(genreGateway.create(any()))
                .thenAnswer(returnsFirstArg());


        // when
        final var actualOutput = useCase.execute(aCommand);

        //then
        Assertions.assertNotNull(actualOutput);
        Assertions.assertNotNull(actualOutput.id());

        verify(categoryGateway, times(1)).existsById(expectedCategories);


        verify(genreGateway, times(1)).create(argThat(genre ->
                Objects.equals(expectedName, genre.getName()) &&
                        Objects.equals(expectedIsActive, genre.isActive()) &&
                        Objects.equals(expectedCategories, genre.getCategories()) &&
                        Objects.nonNull(genre.getId()) &&
                        Objects.nonNull(genre.getCreatedAt()) &&
                        Objects.nonNull(genre.getUpdatedAt()) &&
                        Objects.isNull(genre.getDeletedAt())
        ));

    }

    @Test
    public void givenAInvalidEmptyName_whenCallsCreateCategory_shouldReturnDomainException() {
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

    private List<String> asString(List<CategoryID> categories) {
        return categories.stream().map(CategoryID::getValue)
                .toList();
    }
}
