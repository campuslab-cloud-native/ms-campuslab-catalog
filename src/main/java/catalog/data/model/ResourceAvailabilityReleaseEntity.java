package catalog.data.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "resource_availability_releases")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ResourceAvailabilityReleaseEntity {

    @Id
    @Column(name = "operation_id", nullable = false, updatable = false, length = 128)
    private String operationId;

    @Column(name = "resource_id", nullable = false, updatable = false)
    private Long resourceId;
}
