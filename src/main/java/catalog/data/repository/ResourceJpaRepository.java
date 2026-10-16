package catalog.data.repository;

import catalog.data.model.ResourceEntity;
import catalog.domain.model.ResourceType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ResourceJpaRepository extends JpaRepository<ResourceEntity, Long> {

    List<ResourceEntity> findByType(ResourceType type);

    @Modifying
    @Query("""
            UPDATE ResourceEntity r
            SET r.availableQuantity = r.availableQuantity - 1
            WHERE r.id = :id AND r.availableQuantity > 0
            """)
    int reserveAvailability(@Param("id") Long id);

    @Modifying
    @Query("""
            UPDATE ResourceEntity r
            SET r.availableQuantity = r.availableQuantity + 1
            WHERE r.id = :id
            """)
    int releaseAvailability(@Param("id") Long id);
}
