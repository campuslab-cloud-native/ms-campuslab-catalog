package catalog.data.repository.impl;

import catalog.common.exception.AvailabilityOperationConflictException;
import catalog.data.mapper.ResourceDataMapper;
import catalog.data.model.ResourceAvailabilityReleaseEntity;
import catalog.data.model.ResourceEntity;
import catalog.data.repository.ResourceAvailabilityReleaseJpaRepository;
import catalog.data.repository.ResourceJpaRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ResourceRepositoryImplTest {

    @Mock
    private ResourceJpaRepository resourceJpaRepository;

    @Mock
    private ResourceAvailabilityReleaseJpaRepository availabilityReleaseJpaRepository;

    @Mock
    private ResourceDataMapper resourceDataMapper;

    @Test
    void releasesCupOnceForAnOperation() {
        ResourceEntity resource = ResourceEntity.builder()
                .id(7L)
                .availableQuantity(2)
                .build();
        when(resourceJpaRepository.findByIdForUpdate(7L))
                .thenReturn(Optional.of(resource));
        when(availabilityReleaseJpaRepository.findById("booking:12"))
                .thenReturn(Optional.empty());

        repository().releaseAvailability(7L, "booking:12");

        assertEquals(3, resource.getAvailableQuantity());
        verify(resourceJpaRepository).save(resource);
        verify(availabilityReleaseJpaRepository).save(any(ResourceAvailabilityReleaseEntity.class));
    }

    @Test
    void doesNotReleaseTheSameOperationTwice() {
        when(resourceJpaRepository.findByIdForUpdate(7L))
                .thenReturn(Optional.of(ResourceEntity.builder()
                        .id(7L)
                        .availableQuantity(3)
                        .build()));
        when(availabilityReleaseJpaRepository.findById("booking:12"))
                .thenReturn(Optional.of(ResourceAvailabilityReleaseEntity.builder()
                        .operationId("booking:12")
                        .resourceId(7L)
                        .build()));

        repository().releaseAvailability(7L, "booking:12");

        verify(resourceJpaRepository, never()).save(any(ResourceEntity.class));
        verify(availabilityReleaseJpaRepository, never())
                .save(any(ResourceAvailabilityReleaseEntity.class));
    }

    @Test
    void rejectsReusingAnOperationForAnotherResource() {
        when(resourceJpaRepository.findByIdForUpdate(7L))
                .thenReturn(Optional.of(ResourceEntity.builder()
                        .id(7L)
                        .availableQuantity(3)
                        .build()));
        when(availabilityReleaseJpaRepository.findById("booking:12"))
                .thenReturn(Optional.of(ResourceAvailabilityReleaseEntity.builder()
                        .operationId("booking:12")
                        .resourceId(8L)
                        .build()));

        assertThrows(
                AvailabilityOperationConflictException.class,
                () -> repository().releaseAvailability(7L, "booking:12")
        );

        verify(resourceJpaRepository, never()).save(any(ResourceEntity.class));
    }

    private ResourceRepositoryImpl repository() {
        return new ResourceRepositoryImpl(
                resourceJpaRepository,
                availabilityReleaseJpaRepository,
                resourceDataMapper
        );
    }
}
