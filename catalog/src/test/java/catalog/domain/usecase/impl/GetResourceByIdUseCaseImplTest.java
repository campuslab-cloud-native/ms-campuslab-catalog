package catalog.domain.usecase.impl;

import catalog.common.exception.ResourceNotFoundException;
import catalog.domain.model.Resource;
import catalog.domain.model.ResourceType;
import catalog.domain.repository.ResourceRepository;
import catalog.domain.useCase.GetResourceByIdUseCase;
import catalog.domain.useCase.impl.GetResourcebyIdImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GetResourceByIdUseCaseImplTest {

    @Mock
    private ResourceRepository resourceRepository;

    private GetResourcebyIdImpl useCase;

    @BeforeEach
    void setUp() {
        useCase = new GetResourcebyIdImpl(resourceRepository);
    }

    @Test
    void shouldReturnResourceWhenExists() {

        Resource resource = Resource.builder()
                .id(1L)
                .name("Laboratorio de Redes")
                .type(ResourceType.LAB)
                .availableQuantity(1)
                .build();

        when(resourceRepository.findById(1L))
                .thenReturn(Optional.of(resource));

        Resource result = useCase.execute(1L);

        assertEquals(1L, result.getId());
        assertEquals("Laboratorio de Redes", result.getName());

        verify(resourceRepository).findById(1L);
    }

    @Test
    void shouldThrowExceptionWhenResourceDoesNotExist() {

        when(resourceRepository.findById(99L))
                .thenReturn(Optional.empty());

        assertThrows(
                ResourceNotFoundException.class,
                () -> useCase.execute(99L)
        );

        verify(resourceRepository).findById(99L);
    }
}
