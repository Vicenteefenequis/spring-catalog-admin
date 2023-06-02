package com.fullcycle.admin.catalog.application.castmember.delete;

import com.fullcycle.admin.catalog.domain.Fixture;
import com.fullcycle.admin.catalog.application.UseCaseTest;
import com.fullcycle.admin.catalog.domain.castmember.CastMember;
import com.fullcycle.admin.catalog.domain.castmember.CastMemberGateway;
import com.fullcycle.admin.catalog.domain.castmember.CastMemberID;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

public class DeleteCastMembersUseCaseTest extends UseCaseTest {
    @InjectMocks
    private DefaultDeleteCastMemberUseCase useCase;

    @Mock
    private CastMemberGateway castMemberGateway;

    @Override
    protected List<Object> getMocks() {
        return List.of(castMemberGateway);
    }

    @Test
    public void givenAValidId_whenCallsDeleteCastMember_shouldDeleteIt() {
        //given
        final var aMember = CastMember.newMember(Fixture.name(), Fixture.CastMembers.type());

        final var expectedId = aMember.getId();

        doNothing().when(castMemberGateway).deleteById(any());

        //when

        Assertions.assertDoesNotThrow(() -> useCase.execute(expectedId.getValue()));

        //then
        verify(castMemberGateway, Mockito.times(1)).deleteById(eq(expectedId));
    }


    @Test
    public void givenAnInvalidId_whenCallsDeleteCastMember_shouldBeOk() {
        //given
        final var expectedId = CastMemberID.from("invalid-id");

        doNothing().when(castMemberGateway).deleteById(any());

        //when

        Assertions.assertDoesNotThrow(() -> useCase.execute(expectedId.getValue()));

        //then
        verify(castMemberGateway, Mockito.times(1)).deleteById(eq(expectedId));
    }


    @Test
    public void givenAValidId_whenCallsDeleteCastMemberWhenGatewayThrowsException_shouldReceiveException() {
        //given
        final var aMember = CastMember.newMember(Fixture.name(), Fixture.CastMembers.type());
        final var expectedId = aMember.getId();
        final var expectedErrorMessage = "Gateway error";


        doThrow(new IllegalStateException(expectedErrorMessage)).when(castMemberGateway).deleteById(any());

        //when

        final var actualException = Assertions.assertThrows(IllegalStateException.class,() -> useCase.execute(expectedId.getValue()));


        //then
        Assertions.assertEquals(expectedErrorMessage, actualException.getMessage());
        verify(castMemberGateway, Mockito.times(1)).deleteById(eq(expectedId));
    }
}
