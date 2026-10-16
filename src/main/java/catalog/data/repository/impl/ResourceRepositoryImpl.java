package catalog.data.repository.impl;

import catalog.common.exception.AvailabilityOperationConflictException;
import catalog.data.mapper.ResourceDataMapper;
import catalog.data.model.ResourceAvailabilityReleaseEntity;
import catalog.data.model.ResourceEntity;
import catalog.data.repository.ResourceAvailabilityReleaseJpaRepository;
import catalog.data.repository.ResourceJpaRepository;
import catalog.domain.model.Resource;
import catalog.domain.model.ResourceType;
import catalog.domain.repository.ResourceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class ResourceRepositoryImpl implements ResourceRepository {

    private final ResourceJpaRepository resourceJpaRepository;
    private final ResourceAvailabilityReleaseJpaRepository availabilityReleaseJpaRepository;
    private final ResourceDataMapper resourceDataMapper;


    @Override
    public List<Resource> findAll() {
        return resourceJpaRepository.findAll()
                .stream()
                .map(resourceDataMapper::toDomain)
                .toList();
    }

    @Override
    public List<Resource> findByType(ResourceType type){
        return resourceJpaRepository.findByType(type)
                .stream()
                .map(resourceDataMapper::toDomain)
                .toList();
    }

    @Override
    public Optional<Resource> findById(Long id) {
        return resourceJpaRepository.findById(id)
                .map(resourceDataMapper::toDomain);
    }
    

    @Override
    public Resource save(Resource resource) {
        ResourceEntity entity = resourceDataMapper.toEntity(resource);
        ResourceEntity savedEntity = resourceJpaRepository.save(entity);
        return resourceDataMapper.toDomain(savedEntity);
    }

    @Override
    public boolean reserveAvailability(Long id) {
        return resourceJpaRepository.reserveAvailability(id) == 1;
    }

    @Override
    public boolean releaseAvailability(Long id, String operationId) {
        Optional<ResourceEntity> resource = resourceJpaRepository.findByIdForUpdate(id);
        if (resource.isEmpty()) {
            return false;
        }

        Optional<ResourceAvailabilityReleaseEntity> previousRelease =
                availabilityReleaseJpaRepository.findById(operationId);
        if (previousRelease.isPresent()) {
            if (!previousRelease.get().getResourceId().equals(id)) {
                throw new AvailabilityOperationConflictException(operationId);
            }
            return true;
        }

        ResourceEntity entity = resource.get();
        entity.setAvailableQuantity(entity.getAvailableQuantity() + 1);
        resourceJpaRepository.save(entity);
        availabilityReleaseJpaRepository.save(
                ResourceAvailabilityReleaseEntity.builder()
                        .operationId(operationId)
                        .resourceId(id)
                        .build()
        );
        return true;
    }

}
