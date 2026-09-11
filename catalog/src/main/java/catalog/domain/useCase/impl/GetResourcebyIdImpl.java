package catalog.domain.useCase.impl;

import catalog.common.exception.ResourceNotFoundException;
import catalog.domain.model.Resource;
import catalog.domain.repository.ResourceRepository;
import catalog.domain.useCase.GetResourceByIdUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class GetResourcebyIdImpl implements GetResourceByIdUseCase {

    private final ResourceRepository repository;

    @Override
    public Resource execute(Long id) {
        return repository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(id)
                );
    }
}
