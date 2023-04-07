package com.fullcycle.admin.catalog.application.category.create;

import com.fullcycle.admin.catalog.domain.category.CategoryGateway;
import com.fullcycle.admin.catalog.domain.exceptions.DomainException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Objects;

import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CreateCategoryUseCaseTest {

    @InjectMocks
    private DefaultCreateCategoryUseCase useCase;

    @Mock
    private CategoryGateway categoryGateway;

    // 1. Teste do caminho feliz
    // 2. Teste passando uma propriedade invalida (name)
    // 3. Teste criando uma categoria inativa
    // 4. Teste simulando um error generico vindo do gateway


    @Test
    public void givenAValidCommand_whenCallsCreateCategory_shouldReturnCategoryId() {

        final var expectedName = "filmes";
        final var expectedDescription = "A categoria mais assistida";
        final var expectedIsActive = true;

        final var aCommand = CreateCategoryCommand.with(expectedName, expectedDescription, expectedIsActive);


        when(categoryGateway.create(any()))
                .thenAnswer(returnsFirstArg());


        final var actualOutput = useCase.execute(aCommand);

        Assertions.assertNotNull(actualOutput);
        Assertions.assertNotNull(actualOutput.id());

        Mockito.verify(categoryGateway, times(1)).create(argThat(aCategory ->
                Objects.equals(expectedName, aCategory.getName()) &&
                        Objects.equals(expectedDescription, aCategory.getDescription()) &&
                        Objects.equals(expectedIsActive, aCategory.isActive()) &&
                        Objects.nonNull(aCategory.getId()) &&
                        Objects.nonNull(aCategory.getCreatedAt()) &&
                        Objects.nonNull(aCategory.getUpdatedAt()) &&
                        Objects.isNull(aCategory.getDeletedAt())
        ));
    }

    @Test
    public void givenAInvalidName_whenCallsCreateCategory_thenShouldReturnDomainException() {

        final String expectedName = null;
        final var expectedDescription = "A categoria mais assistida";
        final var expectedIsActive = true;
        final var expectedErrorMessage = "'name' should not be null";
        final var expectedErrorCount = 1;


        final var aCommand = CreateCategoryCommand.with(
                expectedName,
                expectedDescription,
                expectedIsActive
        );

        final var actualException = Assertions.assertThrows(DomainException.class,() -> useCase.execute(aCommand));

        Assertions.assertEquals(expectedErrorMessage,actualException.getMessage());

        Mockito.verify(categoryGateway,times(0)).create(any());
    }
}
