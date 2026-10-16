package catalog.data.repository;

import catalog.data.model.ResourceAvailabilityReleaseEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ResourceAvailabilityReleaseJpaRepository
        extends JpaRepository<ResourceAvailabilityReleaseEntity, String> {
}
