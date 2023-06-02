package com.fullcycle.admin.catalog.application.castmember.retrieve.get;

import com.fullcycle.admin.catalog.domain.Fixture;
import com.fullcycle.admin.catalog.IntegrationTest;
import com.fullcycle.admin.catalog.domain.castmember.CastMember;
import com.fullcycle.admin.catalog.domain.castmember.CastMemberGateway;
import com.fullcycle.admin.catalog.domain.castmember.CastMemberID;
import com.fullcycle.admin.catalog.domain.exceptions.NotFoundException;
import com.fullcycle.admin.catalog.infrastructure.castmember.persistence.CastMemberJpaEntity;
import com.fullcycle.admin.catalog.infrastructure.castmember.persistence.CastMemberRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.SpyBean;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;


@IntegrationTest
public class GetCastMemberByIdUseCaseIT {


    @Autowired
    private GetCastMemberByIdUseCase useCase;

    @SpyBean
    private CastMemberGateway castMemberGateway;


    @Autowired
    private CastMemberRepository castMemberRepository;

    @Test
    public void givenAValidId_whenCallsGetCastMemberById_shouldReturnIt() {
        //given
        final var expectedName = Fixture.name();
        final var expectedType = Fixture.CastMembers.type();

        final var aMember = CastMember.newMember(expectedName, expectedType);

        final var expectedId = aMember.getId();


        this.castMemberRepository.saveAndFlush(CastMemberJpaEntity.from(aMember));

        Assertions.assertEquals(1, this.castMemberRepository.count());

        //when

        final var actualOutput = useCase.execute(expectedId.getValue());

        //then
        Assertions.assertNotNull(actualOutput);
        Assertions.assertEquals(expectedId.getValue(), actualOutput.id());
        Assertions.assertEquals(expectedName, actualOutput.name());
        Assertions.assertEquals(expectedType, actualOutput.type());
        Assertions.assertEquals(aMember.getCreatedAt(), actualOutput.createdAt());
        Assertions.assertEquals(aMember.getUpdatedAt(), actualOutput.updatedAt());

        verify(castMemberGateway).findById(any());
    }

    @Test
    public void givenAInvalidId_whenCallsGetCastMemberAndDoesNotExists_shouldReturnNotFoundException() {
        //given
        final var expectedId = CastMemberID.from("invalid-id");
        final var expectedErrorMessage = "CastMember with ID invalid-id was not found";


        //when

        final var actualException = Assertions.assertThrows(NotFoundException.class, () -> useCase.execute(expectedId.getValue()));

        //then

        Assertions.assertEquals(expectedErrorMessage, actualException.getMessage());

        verify(castMemberGateway, times(1)).findById(eq(expectedId));
    }
}
