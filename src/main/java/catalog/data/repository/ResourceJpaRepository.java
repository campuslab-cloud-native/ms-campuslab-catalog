package catalog.data.repository;

import catalog.data.model.ResourceEntity;
import catalog.domain.model.ResourceType;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ResourceJpaRepository extends JpaRepository<ResourceEntity, Long> {

    List<ResourceEntity> findByType(ResourceType type);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT r FROM ResourceEntity r WHERE r.id = :id")
    Optional<ResourceEntity> findByIdForUpdate(@Param("id") Long id);

    @Modifying
    @Query("""
            UPDATE ResourceEntity r
            SET r.availableQuantity = r.availableQuantity - 1
            WHERE r.id = :id AND r.availableQuantity > 0
            """)
    int reserveAvailability(@Param("id") Long id);

}
