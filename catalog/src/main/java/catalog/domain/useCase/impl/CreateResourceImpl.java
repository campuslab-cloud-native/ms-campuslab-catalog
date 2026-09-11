package catalog.domain.useCase.impl;

import catalog.domain.model.Resource;
import catalog.domain.repository.ResourceRepository;
import catalog.domain.useCase.CreateResourceUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CreateResourceImpl implements CreateResourceUseCase {

    private final ResourceRepository repository;

    @Override
    public Resource execute(Resource resource) {
        return repository.save(resource);
    }
}
