package catalog.domain.repository;

import catalog.domain.model.Resource;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


public interface ResourceRepository {
    List<Resource> findAll();
    Optional<Resource> findById(Long id);
    Resource save(Resource resource);
}
