package catalog.domain.usecase.impl;

import catalog.common.exception.ResourceNotFoundException;
import catalog.common.exception.ResourceUnavailableException;
import catalog.domain.model.Resource;
import catalog.domain.repository.ResourceRepository;
import catalog.domain.useCase.impl.ResourceAvailabilityUseCaseImpl;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class ResourceAvailabilityUseCaseImplTest {

    private final ResourceRepository repository = mock(ResourceRepository.class);
    private final ResourceAvailabilityUseCaseImpl useCase =
            new ResourceAvailabilityUseCaseImpl(repository);

    @Test
    void reservesAvailableCup() {
        when(repository.reserveAvailability(1L)).thenReturn(true);

        useCase.reserve(1L);

        verify(repository).reserveAvailability(1L);
    }

    @Test
    void returnsSpecificErrorWhenNoCupsRemain() {
        when(repository.reserveAvailability(1L)).thenReturn(false);
        when(repository.findById(1L)).thenReturn(Optional.of(Resource.builder().id(1L).build()));

        assertThrows(ResourceUnavailableException.class, () -> useCase.reserve(1L));
    }

    @Test
    void returnsNotFoundWhenResourceDoesNotExist() {
        when(repository.reserveAvailability(1L)).thenReturn(false);
        when(repository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> useCase.reserve(1L));
    }

    @Test
    void releasesCup() {
        when(repository.releaseAvailability(1L, "booking:1")).thenReturn(true);

        useCase.release(1L, "booking:1");

        verify(repository).releaseAvailability(1L, "booking:1");
    }
}
