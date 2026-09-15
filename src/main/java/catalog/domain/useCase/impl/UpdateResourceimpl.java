package catalog.domain.useCase.impl;

import catalog.common.exception.ResourceNotFoundException;
import catalog.domain.model.Resource;
import catalog.domain.repository.ResourceRepository;
import catalog.domain.useCase.UpdateResourceUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class UpdateResourceimpl implements UpdateResourceUseCase {

    private final ResourceRepository repository;

    @Override
    public Resource execute(Long id, Resource resource) {
        Resource existingResource = repository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(id)
        );

        existingResource.setName(resource.getName());
        existingResource.setType(resource.getType());
        existingResource.setAvailableQuantity(resource.getAvailableQuantity());

        return repository.save(existingResource);
    }
}
