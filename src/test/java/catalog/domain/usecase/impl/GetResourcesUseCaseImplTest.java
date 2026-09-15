package catalog.domain.usecase.impl;

import catalog.domain.model.Resource;
import catalog.domain.model.ResourceType;
import catalog.domain.repository.ResourceRepository;
import catalog.domain.useCase.impl.GetResourcesUseCaseImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GetResourcesUseCaseImplTest {

    @Mock
    private ResourceRepository resourceRepository;

    private GetResourcesUseCaseImpl useCase;

    @BeforeEach
    void setUp() {
        useCase = new GetResourcesUseCaseImpl(resourceRepository);
    }

    @Test
    void shouldReturnAllResources() {

        List<Resource> resources = List.of(
                Resource.builder()
                        .id(1L)
                        .name("Laboratorio de Redes")
                        .type(ResourceType.LAB)
                        .availableQuantity(1)
                        .build(),

                Resource.builder()
                        .id(2L)
                        .name("Notebook")
                        .type(ResourceType.EQUIPMENT)
                        .availableQuantity(10)
                        .build()
        );

        when(resourceRepository.findAll())
                .thenReturn(resources);

        List<Resource> result = useCase.execute();

        assertEquals(2, result.size());
        assertEquals("Laboratorio de Redes", result.get(0).getName());

        verify(resourceRepository).findAll();
    }
}