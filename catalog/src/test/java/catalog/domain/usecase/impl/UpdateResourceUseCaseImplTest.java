package catalog.domain.usecase.impl;

import catalog.common.exception.ResourceNotFoundException;
import catalog.domain.model.Resource;
import catalog.domain.model.ResourceType;
import catalog.domain.repository.ResourceRepository;
import catalog.domain.useCase.impl.UpdateResourceimpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UpdateResourceUseCaseImplTest {

    @Mock
    private ResourceRepository resourceRepository;

    private UpdateResourceimpl useCase;

    @BeforeEach
    void setUp() {
        useCase = new UpdateResourceimpl(resourceRepository);
    }

    @Test
    void shouldUpdateResourceWhenExists() {

        Resource existingResource = Resource.builder()
                .id(1L)
                .name("Laboratorio antiguo")
                .type(ResourceType.LAB)
                .availableQuantity(1)
                .build();

        Resource newData = Resource.builder()
                .name("Laboratorio actualizado")
                .type(ResourceType.LAB)
                .availableQuantity(3)
                .build();

        when(resourceRepository.findById(1L))
                .thenReturn(Optional.of(existingResource));

        when(resourceRepository.save(existingResource))
                .thenReturn(existingResource);

        Resource result = useCase.execute(1L, newData);

        assertEquals(1L, result.getId());
        assertEquals("Laboratorio actualizado", result.getName());
        assertEquals(ResourceType.LAB, result.getType());
        assertEquals(3, result.getAvailableQuantity());

        verify(resourceRepository).findById(1L);
        verify(resourceRepository).save(existingResource);
    }

    @Test
    void shouldThrowExceptionWhenResourceDoesNotExist() {

        Resource newData = Resource.builder()
                .name("Laboratorio actualizado")
                .type(ResourceType.LAB)
                .availableQuantity(3)
                .build();

        when(resourceRepository.findById(99L))
                .thenReturn(Optional.empty());

        assertThrows(
                ResourceNotFoundException.class,
                () -> useCase.execute(99L, newData)
        );

        verify(resourceRepository).findById(99L);

        verify(resourceRepository, never())
                .save(any());
    }
}
