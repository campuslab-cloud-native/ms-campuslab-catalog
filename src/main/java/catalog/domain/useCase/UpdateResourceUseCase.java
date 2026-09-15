package catalog.domain.useCase;

import catalog.domain.model.Resource;

public interface UpdateResourceUseCase {

    Resource execute (Long id, Resource resource);
}
