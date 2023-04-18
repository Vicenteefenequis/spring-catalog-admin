package com.fullcycle.admin.catalog.application.genre.retrieve.get;

import com.fullcycle.admin.catalog.application.UseCaseTest;
import com.fullcycle.admin.catalog.domain.category.CategoryID;
import com.fullcycle.admin.catalog.domain.exceptions.NotFoundException;
import com.fullcycle.admin.catalog.domain.genre.Genre;
import com.fullcycle.admin.catalog.domain.genre.GenreGateway;
import com.fullcycle.admin.catalog.domain.genre.GenreID;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class GetGenreByIdUseCaseTest extends UseCaseTest {

    @InjectMocks
    private DefaultGetGenreByIdUseCase useCase;

    @Mock
    private GenreGateway genreGateway;


    @Override
    protected List<Object> getMocks() {
        return List.of(genreGateway);
    }

    @Test
    public void givenAValidId_whenCallsGetGenreById_shouldReturnGenre() {
        final var expectedName = "Ação";
        final var expectedIsActive = true;
        final var expectedCategories = List.of(
                CategoryID.from("123"),
                CategoryID.from("456")
        );

        final var aGenre = Genre.newGenre(
                expectedName,
                expectedIsActive
        );

        aGenre.addCategories(expectedCategories);

        final var expectedId = aGenre.getId();


        Mockito.when(genreGateway.findById(any()))
                .thenReturn(Optional.of(aGenre));


        //when
        final var actualGenre = useCase.execute(expectedId.getValue());

        //then

        Assertions.assertEquals(expectedId.getValue(), actualGenre.id());
        Assertions.assertEquals(expectedName, actualGenre.name());
        Assertions.assertEquals(expectedIsActive, actualGenre.isActive());
        Assertions.assertEquals(asString(aGenre.getCategories()), actualGenre.categories());
        Assertions.assertEquals(aGenre.getCreatedAt(), actualGenre.createdAt());
        Assertions.assertEquals(aGenre.getUpdatedAt(), actualGenre.updatedAt());
        Assertions.assertEquals(aGenre.getDeletedAt(), actualGenre.deletedAt());

        verify(genreGateway, times(1)).findById(eq(expectedId));
    }


    @Test
    public void givenAValidId_whenCallsGetGenreByIdAndDoesNotExists_shouldReturnNotFound() {

        final var expectedErrorMessage = "Genre with ID 123 was not found";
        final var anId = GenreID.from("123");

        Mockito.when(genreGateway.findById(eq(anId)))
                .thenReturn(Optional.empty());


        //when
        final var actualException = Assertions.assertThrows(
                NotFoundException.class,
                () -> useCase.execute(anId.getValue())
        );

        //then
        Assertions.assertEquals(expectedErrorMessage, actualException.getMessage());
        verify(genreGateway, times(1)).findById(eq(anId));
    }


    private List<String> asString(final List<CategoryID> ids) {
        return ids.stream()
                .map(CategoryID::getValue)
                .toList();
    }
}
