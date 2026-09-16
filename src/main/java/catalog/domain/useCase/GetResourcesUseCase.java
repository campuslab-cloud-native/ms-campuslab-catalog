package catalog.domain.useCase;

import catalog.domain.model.Resource;
import catalog.domain.model.ResourceType;

import java.util.List;

public interface GetResourcesUseCase {

    List<Resource> execute(ResourceType type);
}
