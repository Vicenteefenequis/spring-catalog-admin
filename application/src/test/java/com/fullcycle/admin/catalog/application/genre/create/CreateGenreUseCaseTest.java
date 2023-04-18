package com.fullcycle.admin.catalog.application.genre.create;


import com.fullcycle.admin.catalog.domain.category.CategoryGateway;
import com.fullcycle.admin.catalog.domain.category.CategoryID;
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

    private List<String> asString(List<CategoryID> categories) {
        return categories.stream().map(CategoryID::getValue)
                .toList();
    }
}
