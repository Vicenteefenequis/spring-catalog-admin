package com.fullcycle.admin.catalog.application.castmember.create;

import com.fullcycle.admin.catalog.domain.Fixture;
import com.fullcycle.admin.catalog.IntegrationTest;
import com.fullcycle.admin.catalog.domain.castmember.CastMemberGateway;
import com.fullcycle.admin.catalog.domain.castmember.CastMemberType;
import com.fullcycle.admin.catalog.domain.exceptions.NotificationException;
import com.fullcycle.admin.catalog.infrastructure.castmember.persistence.CastMemberRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.SpyBean;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@IntegrationTest
public class CreateCastMemberUseCaseIT  {

    @Autowired
    private CreateCastMemberUseCase useCase;

    @SpyBean
    private CastMemberGateway castMemberGateway;

    @Autowired
    private CastMemberRepository castMemberRepository;



    @Test
    public void givenAValidCommand_whenCallsCreateCastMember_shouldReturnIt() {
        //given
        final var expectedName = Fixture.name();
        final var expectedType = Fixture.CastMembers.type();

        final var aCommand = new CreateCastMemberCommand(
                expectedName,
                expectedType
        );


        //when
        final var actualOutput = useCase.execute(aCommand);

        //then
        Assertions.assertNotNull(actualOutput);
        Assertions.assertNotNull(actualOutput.id());

        final var actualMember = this.castMemberRepository.findById(actualOutput.id()).get();

        Assertions.assertNotNull(actualMember);
        Assertions.assertEquals(expectedName, actualMember.getName());
        Assertions.assertEquals(expectedType, actualMember.getType());
        Assertions.assertNotNull(actualMember.getCreatedAt());
        Assertions.assertNotNull(actualMember.getUpdatedAt());

       verify(castMemberGateway,times(1)).create(any());

    }


    @Test
    public void givenAInvalidName_whenCallsCreateCastMember_shouldThrowsNotificationException() {
        final String expectedName = null;
        final var expectedType = Fixture.CastMembers.type();

        final var expectedErrorCount = 1;
        final var expectedErrorMessage = "'name' should not be null";

        final var aCommand = new CreateCastMemberCommand(
                expectedName,
                expectedType
        );

        final var actualException = Assertions.assertThrows(
                NotificationException.class,
                () -> useCase.execute(aCommand)
        );

        Assertions.assertNotNull(actualException);
        Assertions.assertEquals(expectedErrorCount, actualException.getErrors().size());
        Assertions.assertEquals(expectedErrorMessage, actualException.getErrors().get(0).message());

        verify(castMemberGateway, never()).create(any());
    }

    @Test
    public void givenAInvalidType_whenCallsCreateCastMember_shouldThrowsNotificationException() {
        final String expectedName = Fixture.name();
        final CastMemberType expectedType = null;

        final var expectedErrorCount = 1;
        final var expectedErrorMessage = "'type' should not be null";

        final var aCommand = new CreateCastMemberCommand(
                expectedName,
                expectedType
        );

        final var actualException = Assertions.assertThrows(
                NotificationException.class,
                () -> useCase.execute(aCommand)
        );

        Assertions.assertNotNull(actualException);
        Assertions.assertEquals(expectedErrorCount, actualException.getErrors().size());
        Assertions.assertEquals(expectedErrorMessage, actualException.getErrors().get(0).message());

        verify(castMemberGateway, never()).create(any());
    }
}
