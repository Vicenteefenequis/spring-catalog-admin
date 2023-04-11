package com.fullcycle.admin.catalog.domain.genre;


import com.fullcycle.admin.catalog.domain.exceptions.NotificationException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class GenreTest {


    @Test
    public void givenValidParams_whenCallNewGenre_shouldInstantiateGenre() {
        // given
        final var expectedName = "Ação";
        final var expectedIsActive = true;
        final var expectedCategories = 0;

        // when
        final var actualGenre = Genre.newGenre(expectedName, expectedIsActive);

        // then
        Assertions.assertNotNull(actualGenre);
        Assertions.assertNotNull(actualGenre.getId());
        Assertions.assertEquals(expectedName, actualGenre.getName());
        Assertions.assertEquals(expectedIsActive, actualGenre.isActive());
        Assertions.assertEquals(expectedCategories, actualGenre.getCategories().size());
        Assertions.assertNotNull(actualGenre.getCreatedAt());
        Assertions.assertNotNull(actualGenre.getUpdatedAt());
        Assertions.assertNull(actualGenre.getDeletedAt());
    }

    @Test
    public void givenInvalidNull_whenCallNewGenreAndValidate_shouldReceiveAError() {
        // given
        final String expectedName = null;
        final var expectedIsActive = false;
        final var expectedErrorCount = 1;
        final var expectedErrorMessage = "'name' should not be null";

        // when

        final var actualException = Assertions.assertThrows(NotificationException.class, () -> {
           Genre.newGenre(expectedName, expectedIsActive);
        });

        // then

        Assertions.assertEquals(expectedErrorCount, actualException.getErrors().size());
        Assertions.assertEquals(expectedErrorMessage, actualException.getErrors().get(0).message());
    }


    @Test
    public void givenInvalidEmptyName_whenCallNewGenreAndValidate_shouldReceiveAError() {
        // given
        final var expectedName = " ";
        final var expectedIsActive = false;
        final var expectedErrorCount = 1;
        final var expectedErrorMessage = "'name' should not be empty";

        // when


        final var actualException = Assertions.assertThrows(NotificationException.class, () -> {
            Genre.newGenre(expectedName, expectedIsActive);
        });


        // then

        Assertions.assertEquals(expectedErrorCount, actualException.getErrors().size());
        Assertions.assertEquals(expectedErrorMessage, actualException.getErrors().get(0).message());
    }


    @Test
    public void givenInvalidNameWithLengthGreaterThan255_whenCallNewGenreAndValidate_shouldReceiveAError() {
        // given
        final var expectedName = "" +
                "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Donec auctor, " +
                "nisl eget ultricies lacinia, nisl nisl lacinia nisl, eget ultricies " +
                "nisl nisl eget nisl. Donec auctor, nisl eget ultricies lacinia, nisl" +
                "nisl lacinia nisl, eget ultricies nisl nisl eget nisl. Donec auctor";
        final var expectedIsActive = false;
        final var expectedErrorCount = 1;
        final var expectedErrorMessage = "'name' must be between 1 and 255 characteres";

        // when

        final var actualException = Assertions.assertThrows(NotificationException.class, () -> {
            Genre.newGenre(expectedName, expectedIsActive);
        });


        // then

        Assertions.assertEquals(expectedErrorCount, actualException.getErrors().size());
        Assertions.assertEquals(expectedErrorMessage, actualException.getErrors().get(0).message());
    }
}
