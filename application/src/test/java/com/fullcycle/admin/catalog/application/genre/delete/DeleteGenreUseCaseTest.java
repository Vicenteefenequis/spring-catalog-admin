package com.fullcycle.admin.catalog.application.genre.delete;

import com.fullcycle.admin.catalog.application.UseCaseTest;
import com.fullcycle.admin.catalog.domain.genre.Genre;
import com.fullcycle.admin.catalog.domain.genre.GenreGateway;
import com.fullcycle.admin.catalog.domain.genre.GenreID;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;

public class DeleteGenreUseCaseTest extends UseCaseTest {

    @InjectMocks
    private DefaultDeleteGenreUseCase useCase;

    @Mock
    private GenreGateway genreGateway;


    @Override
    protected List<Object> getMocks() {
        return List.of(genreGateway);
    }

    @Test
    public void givenAValidGenreId_whenCallsDeleteGenre_shouldDeleteGenre() {

        final var aGenre = Genre.newGenre("Ação", true);

        final var expectedId = aGenre.getId();

        doNothing().when(genreGateway).deleteById(any());

        //when
        Assertions.assertDoesNotThrow(() -> useCase.execute(expectedId.getValue()));

        //then
        Mockito.verify(genreGateway, Mockito.times(1)).deleteById(expectedId);
    }


    @Test
    public void givenAnInvalidGenreId_whenCallsDeleteGenre_shouldBeOk() {

        final var expectedId = GenreID.from("123");

        doNothing().when(genreGateway).deleteById(any());

        //when
        Assertions.assertDoesNotThrow(() -> useCase.execute(expectedId.getValue()));

        //then
        Mockito.verify(genreGateway, Mockito.times(1)).deleteById(expectedId);
    }


    @Test
    public void givenValidGenreId_whenCallsDeleteGenreAndGatewayThrowsUnexpectedError_shouldReceiveException() {

        final var expectedId = GenreID.from("123");

        final var expectedErrorMessage = "Gateway Error";

        doThrow(new IllegalStateException(expectedErrorMessage)).when(genreGateway).deleteById(any());

        //when
        Assertions.assertThrows(IllegalStateException.class, () -> useCase.execute(expectedId.getValue()));

        //then
        Mockito.verify(genreGateway, Mockito.times(1)).deleteById(expectedId);
    }
}
