package catalog.domain.repository;

import catalog.domain.model.Resource;
import catalog.domain.model.ResourceType;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


public interface ResourceRepository {
    List<Resource> findAll();
    List<Resource> findByType(ResourceType type);
    Optional<Resource> findById(Long id);
    Resource save(Resource resource);
}
