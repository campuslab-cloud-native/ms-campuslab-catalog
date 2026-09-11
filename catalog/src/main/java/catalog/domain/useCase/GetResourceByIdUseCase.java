package catalog.domain.useCase;

import catalog.domain.model.Resource;

public interface GetResourceByIdUseCase {

    Resource resource(Long id);
}
