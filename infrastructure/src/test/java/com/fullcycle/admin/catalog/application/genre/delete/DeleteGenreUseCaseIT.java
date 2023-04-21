package com.fullcycle.admin.catalog.application.genre.delete;

import com.fullcycle.admin.catalog.IntegrationTest;
import com.fullcycle.admin.catalog.domain.genre.Genre;
import com.fullcycle.admin.catalog.domain.genre.GenreGateway;
import com.fullcycle.admin.catalog.infrastructure.genre.persistence.GenreRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

@IntegrationTest
public class DeleteGenreUseCaseIT {
    @Autowired
    private DefaultDeleteGenreUseCase useCase;
    @Autowired
    private GenreGateway genreGateway;
    @Autowired
    private GenreRepository genreRepository;

    @Test
    public void givenAValidGenreId_whenCallsDeleteGenre_shouldDeleteGenre() {

        final var aGenre = genreGateway.create(Genre.newGenre("Ação", true));

        final var expectedId = aGenre.getId();

        Assertions.assertEquals(1, genreRepository.count());

        //when
        Assertions.assertDoesNotThrow(() -> useCase.execute(expectedId.getValue()));


        //then
        Assertions.assertEquals(0, genreRepository.count());
    }


    @Test
    public void givenAnInvalidGenreId_whenCallsDeleteGenre_shouldBeOk() {

        final var aGenre = genreGateway.create(Genre.newGenre("Ação", true));

        Assertions.assertEquals(1, genreRepository.count());
        //when
        Assertions.assertDoesNotThrow(() -> useCase.execute("123"));

        //then
        Assertions.assertEquals(1, genreRepository.count());

    }


}
