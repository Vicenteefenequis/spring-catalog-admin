package com.fullcycle.admin.catalog.application.castmember.create;

import com.fullcycle.admin.catalog.domain.Fixture;
import com.fullcycle.admin.catalog.application.UseCaseTest;
import com.fullcycle.admin.catalog.domain.castmember.CastMemberGateway;
import com.fullcycle.admin.catalog.domain.castmember.CastMemberType;
import com.fullcycle.admin.catalog.domain.exceptions.NotificationException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.List;
import java.util.Objects;

import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class CreateCastMembersUseCaseTest extends UseCaseTest {

    @InjectMocks
    private DefaultCreateCastMemberUseCase useCase;

    @Mock
    private CastMemberGateway castMemberGateway;

    @Override
    protected List<Object> getMocks() {
        return List.of(castMemberGateway);
    }

    @Test
    public void givenAValidCommand_whenCallsCreateCastMember_shouldReturnIt() {
        //given
        final var expectedName = Fixture.name();
        final var expectedType = Fixture.CastMembers.type();

        final var aCommand = new CreateCastMemberCommand(
                expectedName,
                expectedType
        );

        when(castMemberGateway.create(any()))
                .thenAnswer(returnsFirstArg());

        //when
        final var actualOutput = useCase.execute(aCommand);

        //then
        Assertions.assertNotNull(actualOutput);
        Assertions.assertNotNull(actualOutput.id());

        verify(castMemberGateway, times(1)).create(argThat(castMember ->
                Objects.nonNull(castMember.getId()) &&
                        Objects.equals(expectedName, castMember.getName()) &&
                        Objects.equals(expectedType, castMember.getType()) &&
                        Objects.nonNull(castMember.getCreatedAt()) &&
                        Objects.nonNull(castMember.getUpdatedAt())
        ));

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
