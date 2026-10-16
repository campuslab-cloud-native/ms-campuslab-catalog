package catalog.domain.useCase.impl;

import catalog.common.exception.ResourceNotFoundException;
import catalog.common.exception.ResourceUnavailableException;
import catalog.domain.repository.ResourceRepository;
import catalog.domain.useCase.ResourceAvailabilityUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ResourceAvailabilityUseCaseImpl implements ResourceAvailabilityUseCase {

    private final ResourceRepository repository;

    @Override
    @Transactional
    public void reserve(Long id) {
        if (repository.reserveAvailability(id)) {
            return;
        }

        if (repository.findById(id).isEmpty()) {
            throw new ResourceNotFoundException(id);
        }

        throw new ResourceUnavailableException(id);
    }

    @Override
    @Transactional
    public void release(Long id) {
        if (!repository.releaseAvailability(id)) {
            throw new ResourceNotFoundException(id);
        }
    }
}
