package catalog.presentation.controller;

import catalog.domain.useCase.CreateResourceUseCase;
import catalog.domain.useCase.GetResourceByIdUseCase;
import catalog.domain.useCase.GetResourcesUseCase;
import catalog.domain.useCase.UpdateResourceUseCase;
import catalog.presentation.mapper.ResourcePresentationMapper;
import catalog.presentation.request.ResourceRequest;
import catalog.presentation.response.ResourceResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/catalog/resources")
@RequiredArgsConstructor
public class ResourceController {

    private final GetResourcesUseCase getResourcesUseCase;
    private final GetResourceByIdUseCase getResourceByIdUseCase;
    private final CreateResourceUseCase createResourceUseCase;
    private final UpdateResourceUseCase updateResourceUseCase;
    private final ResourcePresentationMapper resourcePresentationMapper;

    @GetMapping
    public ResponseEntity<List<ResourceResponse>> getAllResources() {
        var resources = getResourcesUseCase.execute()
                .stream()
                .map(resourcePresentationMapper::toResponse)
                .toList();
        return ResponseEntity.ok(resources);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResourceResponse> getResourceById(
            @PathVariable Long id) {
        var resource = getResourceByIdUseCase.execute(id);

        return ResponseEntity.ok(
                resourcePresentationMapper.toResponse(resource)
        );
    }

    @PostMapping
    public ResponseEntity<ResourceResponse> createResource(@Valid @RequestBody ResourceRequest request) {
        var resource = resourcePresentationMapper.toDomain(request);

        var saveResource = createResourceUseCase.execute(resource);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(
                    resourcePresentationMapper.toResponse(saveResource)
                );

    }

    @PutMapping("/{id}")
    public ResponseEntity<ResourceResponse> updateResource(
            @PathVariable Long id,
            @Valid @RequestBody ResourceRequest request
    ) {
        var resource = resourcePresentationMapper.toDomain(request);

        var updateResource = updateResourceUseCase.execute(id, resource);

        return ResponseEntity.ok(
                resourcePresentationMapper.toResponse(updateResource)
        );
    }
}
