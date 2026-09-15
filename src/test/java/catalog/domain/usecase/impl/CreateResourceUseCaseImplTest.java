package catalog.domain.usecase.impl;

import catalog.domain.model.Resource;
import catalog.domain.model.ResourceType;
import catalog.domain.repository.ResourceRepository;
import catalog.domain.useCase.CreateResourceUseCase;
import catalog.domain.useCase.impl.CreateResourceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CreateResourceUseCaseImplTest {

    @Mock
    private ResourceRepository resourceRepository;

    private CreateResourceImpl useCase;

    @BeforeEach
    void setUp() {
        useCase = new CreateResourceImpl(resourceRepository);
    }

    @Test
    void shouldCreateResource() {

        Resource resource = Resource.builder()
                .name("Notebook")
                .type(ResourceType.EQUIPMENT)
                .availableQuantity(10)
                .build();

        Resource savedResource = Resource.builder()
                .id(1L)
                .name("Notebook")
                .type(ResourceType.EQUIPMENT)
                .availableQuantity(10)
                .build();

        when(resourceRepository.save(resource))
                .thenReturn(savedResource);

        Resource result = useCase.execute(resource);

        assertEquals(1L, result.getId());
        assertEquals("Notebook", result.getName());
        assertEquals(ResourceType.EQUIPMENT, result.getType());
        assertEquals(10, result.getAvailableQuantity());

        verify(resourceRepository).save(resource);
    }
}
