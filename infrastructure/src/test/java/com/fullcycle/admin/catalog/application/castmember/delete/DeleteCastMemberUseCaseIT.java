package com.fullcycle.admin.catalog.application.castmember.delete;

import com.fullcycle.admin.catalog.domain.Fixture;
import com.fullcycle.admin.catalog.IntegrationTest;
import com.fullcycle.admin.catalog.domain.castmember.CastMember;
import com.fullcycle.admin.catalog.domain.castmember.CastMemberGateway;
import com.fullcycle.admin.catalog.domain.castmember.CastMemberID;
import com.fullcycle.admin.catalog.infrastructure.castmember.persistence.CastMemberJpaEntity;
import com.fullcycle.admin.catalog.infrastructure.castmember.persistence.CastMemberRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.SpyBean;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;

@IntegrationTest
public class DeleteCastMemberUseCaseIT {
    @Autowired
    private DefaultDeleteCastMemberUseCase useCase;

    @SpyBean
    private CastMemberGateway castMemberGateway;


    @Autowired
    private CastMemberRepository castMemberRepository;

    @Test
    public void givenAValidId_whenCallsDeleteCastMember_shouldDeleteIt() {
        //given
        final var aMember = CastMember.newMember(Fixture.name(),Fixture.CastMembers.type());
        final var aMember2 = CastMember.newMember(Fixture.name(),Fixture.CastMembers.type());

        final var expectedId = aMember.getId();

        this.castMemberRepository.saveAndFlush(CastMemberJpaEntity.from(aMember));
        this.castMemberRepository.saveAndFlush(CastMemberJpaEntity.from(aMember2));

        Assertions.assertEquals(2, this.castMemberRepository.count());

        //when

        Assertions.assertDoesNotThrow(() -> useCase.execute(expectedId.getValue()));

        //then
        Assertions.assertEquals(1, this.castMemberRepository.count());
        Assertions.assertFalse(this.castMemberRepository.existsById(expectedId.getValue()));
        Assertions.assertTrue(this.castMemberRepository.existsById(aMember2.getId().getValue()));
        verify(castMemberGateway, Mockito.times(1)).deleteById(eq(expectedId));
    }


    @Test
    public void givenAnInvalidId_whenCallsDeleteCastMember_shouldBeOk() {
        //given
        final var expectedId = CastMemberID.from("invalid-id");
        final var aMember = CastMember.newMember(Fixture.name(),Fixture.CastMembers.type());
        this.castMemberRepository.saveAndFlush(CastMemberJpaEntity.from(aMember));
        //when

        Assertions.assertDoesNotThrow(() -> useCase.execute(expectedId.getValue()));

        //then
        verify(castMemberGateway, Mockito.times(1)).deleteById(eq(expectedId));
        Assertions.assertEquals(1, this.castMemberRepository.count());
    }


    @Test
    public void givenAValidId_whenCallsDeleteCastMemberWhenGatewayThrowsException_shouldReceiveException() {
        //given
        final var aMember = CastMember.newMember(Fixture.name(),Fixture.CastMembers.type());
        final var expectedId = aMember.getId();
        final var expectedErrorMessage = "Gateway error";

        this.castMemberRepository.saveAndFlush(CastMemberJpaEntity.from(aMember));

        Assertions.assertEquals(1, this.castMemberRepository.count());

        doThrow(new IllegalStateException(expectedErrorMessage)).when(castMemberGateway).deleteById(any());

        //when

        final var actualException = Assertions.assertThrows(IllegalStateException.class,() -> useCase.execute(expectedId.getValue()));


        //then
        Assertions.assertEquals(expectedErrorMessage, actualException.getMessage());
        verify(castMemberGateway, Mockito.times(1)).deleteById(eq(expectedId));
        Assertions.assertEquals(1, this.castMemberRepository.count());
    }
}
