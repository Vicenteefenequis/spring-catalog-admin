package com.fullcycle.admin.catalog.application.category.update;

import com.fullcycle.admin.catalog.domain.category.Category;
import com.fullcycle.admin.catalog.domain.category.CategoryGateway;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Objects;
import java.util.Optional;

import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UpdateCategoryUseCaseTest {
    @InjectMocks
    private DefaultUpdateCategoryUseCase useCase;

    @Mock
    private CategoryGateway categoryGateway;

    // 1. Teste do caminho feliz
    // 2. Teste passando uma propriedade invalida (name)
    // 3. Teste atualizando uma categoria inativa
    // 4. Teste simulando um error generico vindo do gateway
    // 5. Teste atualizar categoria passando ID invalido

    @Test
    public void givenAValidCommand_whenCallsUpdateCategory_shouldReturnCategoryId() {
        final var aCategory = Category.newCategory("Film", null, true);

        final var expectedName = "filmes";
        final var expectedDescription = "A categoria mais assistida";
        final var expectedIsActive = true;


        final var expectedId = aCategory.getId();

        final var aCommand = UpdateCategoryCommand.with(
                expectedId.getValue(),
                expectedName,
                expectedDescription,
                expectedIsActive
        );


        when(categoryGateway.findById(eq(expectedId)))
                .thenReturn(Optional.of(Category.clone(aCategory)));

        when(categoryGateway.update(any())).
                thenAnswer(returnsFirstArg());

        final var actualOutput = useCase.execute(aCommand).get();

        Assertions.assertNotNull(actualOutput);
        Assertions.assertNotNull(actualOutput.id());

        verify(categoryGateway,times(1)).findById(eq(expectedId));

        verify(categoryGateway,times(1)).update(argThat(
                aUpdateCategory ->
                        Objects.equals(expectedName, aUpdateCategory.getName()) &&
                                Objects.equals(expectedDescription, aUpdateCategory.getDescription()) &&
                                Objects.equals(expectedIsActive, aUpdateCategory.isActive()) &&
                                Objects.equals(expectedId,aUpdateCategory.getId()) &&
                                Objects.equals(aCategory.getCreatedAt(),aUpdateCategory.getCreatedAt()) &&
                                aCategory.getUpdatedAt().isBefore(aUpdateCategory.getUpdatedAt()) &&
                                Objects.isNull(aUpdateCategory.getDeletedAt())
        ));

    }


}
