package catalog.presentation.mapper;

import catalog.data.model.ResourceEntity;
import catalog.domain.model.Resource;
import catalog.presentation.request.ResourceRequest;
import catalog.presentation.response.ResourceResponse;
import org.springframework.stereotype.Component;

@Component
public class ResourcePresentationMapper {

    public Resource toDomain(ResourceRequest request){
        return Resource.builder()
                .name(request.getName())
                .type(request.getType())
                .availableQuantity(request.getAvailableQuantity())
                .build();
    }

    public ResourceResponse toResponse(Resource resource) {
        return ResourceResponse.builder()
                .id(resource.getId())
                .name(resource.getName())
                .type(resource.getType())
                .availableQuantity(resource.getAvailableQuantity())
                .build();
    }
}
