package catalog.domain.useCase.impl;

import catalog.domain.model.Resource;
import catalog.domain.repository.ResourceRepository;
import catalog.domain.useCase.GetResourcesUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GetResourcesUseCaseImpl implements GetResourcesUseCase {

    private final ResourceRepository repository;

    @Override
    public List<Resource> execute() {
        return repository.findAll();
    }
}
