package catalog.data.mapper;

import catalog.data.model.ResourceEntity;
import catalog.domain.model.Resource;
import org.springframework.stereotype.Component;

@Component
public class ResourceDataMapper {

    public ResourceEntity toEntity(Resource resource) {
        return ResourceEntity.builder()
                .id(resource.getId())
                .name(resource.getName())
                .type(resource.getType())
                .availableQuantity(resource.getAvailableQuantity())
                .build();
    }

    public Resource toDomain(ResourceEntity entity) {
        return Resource.builder()
                .id(entity.getId())
                .name(entity.getName())
                .type(entity.getType())
                .availableQuantity(entity.getAvailableQuantity())
                .build();
    }
}
