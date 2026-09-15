package catalog.data.repository.impl;

import catalog.data.mapper.ResourceDataMapper;
import catalog.data.model.ResourceEntity;
import catalog.data.repository.ResourceJpaRepository;
import catalog.domain.model.Resource;
import catalog.domain.repository.ResourceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class ResourceRepositoryImpl implements ResourceRepository {

    private final ResourceJpaRepository resourceJpaRepository;
    private final ResourceDataMapper resourceDataMapper;


    @Override
    public List<Resource> findAll() {
        return resourceJpaRepository.findAll()
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



}
