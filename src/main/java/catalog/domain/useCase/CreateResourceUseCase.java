package catalog.domain.useCase;

import catalog.domain.model.Resource;

public interface CreateResourceUseCase {

    Resource execute (Resource resource);
}
