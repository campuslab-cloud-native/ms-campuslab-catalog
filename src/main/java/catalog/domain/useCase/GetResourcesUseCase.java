package catalog.domain.useCase;

import catalog.domain.model.Resource;

import java.util.List;

public interface GetResourcesUseCase {

    List<Resource> execute();
}
