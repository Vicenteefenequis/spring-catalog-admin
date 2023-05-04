package com.fullcycle.admin.catalog.application.video.delete;

import com.fullcycle.admin.catalog.application.UseCaseTest;
import com.fullcycle.admin.catalog.domain.exceptions.InternalErrorException;
import com.fullcycle.admin.catalog.domain.video.VideoGateway;
import com.fullcycle.admin.catalog.domain.video.VideoID;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;

public class DeleteVideoUseCaseTest extends UseCaseTest {

    @InjectMocks
    private  DefaultDeleteVideoUseCase useCase;

    @Mock
    private VideoGateway videoGateway;

    @Override
    protected List<Object> getMocks() {
        return List.of(videoGateway);
    }


    @Test
    public void givenAValidId_whenCallsDeleteVideo_shouldDeleteIt()  {
        //given
        final var expectedId = VideoID.unique();

        Mockito.doNothing().when(videoGateway).deleteById(any());

        //when

        Assertions.assertDoesNotThrow(() -> useCase.execute(expectedId.getValue()));

        //then

        verify(videoGateway).deleteById(eq(expectedId));
    }

    @Test
    public void givenAnInvalidId_whenCallsDeleteVideo_shouldBeOk() {
        //given
        final var expectedId = VideoID.from("123");

        Mockito.doNothing().when(videoGateway).deleteById(any());

        //when

        Assertions.assertDoesNotThrow(() -> useCase.execute(expectedId.getValue()));

        //then

        verify(videoGateway).deleteById(eq(expectedId));
    }



    @Test
    public void givenAValidId_whenCallsDeleteVideoAndGatewayThrowsException_shouldReceiveException() {
        //given
        final var expectedId = VideoID.from("123");

        doThrow(InternalErrorException.with("Error on delete video",new RuntimeException())).when(videoGateway).deleteById(any());

        //when

        Assertions.assertThrows(InternalErrorException.class,() -> useCase.execute(expectedId.getValue()));

        //then

        verify(videoGateway).deleteById(eq(expectedId));
    }
}
