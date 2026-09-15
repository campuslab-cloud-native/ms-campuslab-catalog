package catalog.domain.useCase;

import catalog.domain.model.Resource;

public interface GetResourceByIdUseCase {

    Resource execute(Long id);
}
