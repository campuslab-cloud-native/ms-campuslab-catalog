package catalog.data.repository;

import catalog.data.model.ResourceEntity;
import catalog.domain.model.Resource;
import catalog.domain.model.ResourceType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ResourceJpaRepository extends JpaRepository<ResourceEntity, Long> {

    List<ResourceEntity> findByType(ResourceType type);
}
