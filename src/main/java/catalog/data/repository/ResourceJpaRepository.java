package catalog.data.repository;

import catalog.data.model.ResourceEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ResourceJpaRepository extends JpaRepository<ResourceEntity, Long> {
}
