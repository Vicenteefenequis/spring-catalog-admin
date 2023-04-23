package com.fullcycle.admin.catalog.domain.castmember;

import com.fullcycle.admin.catalog.domain.exceptions.NotificationException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class CastMemberTest {

    @Test
    public void givenAValidParams_whenCallsNewMember_thenInstantiateACastMember() {
        final var expectedName = "Vin Diesel";
        final var expectedType = CastMemberType.ACTOR;

        final var actualMember = CastMember.newMember(expectedName, expectedType);

        Assertions.assertNotNull(actualMember);
        Assertions.assertNotNull(actualMember.getId());
        Assertions.assertEquals(expectedName, actualMember.getName());
        Assertions.assertEquals(expectedType, actualMember.getType());
        Assertions.assertNotNull(actualMember.getCreatedAt());
        Assertions.assertNotNull(actualMember.getUpdatedAt());
    }

    @Test
    public void givenAInvalidNullName_whenCallsNewMember_thenThrowsException() {
        final String expectedName = null;
        final var expectedType = CastMemberType.ACTOR;
        final var expectedErrorCount = 1;
        final var expectedErrorMessage = "'name' should not be null";

        final var actualException = Assertions.assertThrows(
                NotificationException.class,
                () -> CastMember.newMember(expectedName, expectedType)
        );

        Assertions.assertNotNull(actualException);
        Assertions.assertEquals(expectedErrorMessage, actualException.getErrors().get(0).message());
        Assertions.assertEquals(expectedErrorCount, actualException.getErrors().size());
    }

    @Test
    public void givenAInvalidEmptyName_whenCallsNewMember_thenThrowsException() {
        final var expectedName = " ";
        final var expectedType = CastMemberType.ACTOR;
        final var expectedErrorCount = 1;
        final var expectedErrorMessage = "'name' should not be empty";

        final var actualException = Assertions.assertThrows(
                NotificationException.class,
                () -> CastMember.newMember(expectedName, expectedType)
        );

        Assertions.assertNotNull(actualException);
        Assertions.assertEquals(expectedErrorMessage, actualException.getErrors().get(0).message());
        Assertions.assertEquals(expectedErrorCount, actualException.getErrors().size());
    }

    @Test
    public void givenAInvalidNameWithLengthMoreThan255_whenCallsNewMember_thenThrowsException() {
        final var expectedName = "" +
                "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Donec auctor, " +
                "nisl eget ultricies lacinia, nisl nisl lacinia nisl, eget ultricies " +
                "nisl nisl eget nisl. Donec auctor, nisl eget ultricies lacinia, nisl" +
                "nisl lacinia nisl, eget ultricies nisl nisl eget nisl. Donec auctor";
        final var expectedType = CastMemberType.ACTOR;
        final var expectedErrorCount = 1;
        final var expectedErrorMessage = "'name' must be between 3 and 255 characters";

        final var actualException = Assertions.assertThrows(
                NotificationException.class,
                () -> CastMember.newMember(expectedName, expectedType)
        );

        Assertions.assertNotNull(actualException);
        Assertions.assertEquals(expectedErrorMessage, actualException.getErrors().get(0).message());
        Assertions.assertEquals(expectedErrorCount, actualException.getErrors().size());
    }

    @Test
    public void givenAInvalidNullType_whenCallsNewMember_thenThrowsException() {
        final var expectedName = "Vin Diesel";
        final CastMemberType expectedType = null;
        final var expectedErrorCount = 1;
        final var expectedErrorMessage = "'type' should not be null";

        final var actualException = Assertions.assertThrows(
                NotificationException.class,
                () -> CastMember.newMember(expectedName, expectedType)
        );

        Assertions.assertNotNull(actualException);
        Assertions.assertEquals(expectedErrorMessage, actualException.getErrors().get(0).message());
        Assertions.assertEquals(expectedErrorCount, actualException.getErrors().size());
    }

}
