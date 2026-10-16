package catalog.presentation.controller;

import catalog.domain.model.ResourceType;
import catalog.domain.useCase.CreateResourceUseCase;
import catalog.domain.useCase.GetResourceByIdUseCase;
import catalog.domain.useCase.GetResourcesUseCase;
import catalog.domain.useCase.ResourceAvailabilityUseCase;
import catalog.domain.useCase.UpdateResourceUseCase;
import catalog.presentation.mapper.ResourcePresentationMapper;
import catalog.presentation.request.ResourceRequest;
import catalog.presentation.response.ResourceResponse;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Validated
@RequestMapping("api/catalog/resources")
@RequiredArgsConstructor
public class ResourceController {

    private final GetResourcesUseCase getResourcesUseCase;
    private final GetResourceByIdUseCase getResourceByIdUseCase;
    private final CreateResourceUseCase createResourceUseCase;
    private final UpdateResourceUseCase updateResourceUseCase;
    private final ResourceAvailabilityUseCase resourceAvailabilityUseCase;
    private final ResourcePresentationMapper resourcePresentationMapper;

    @GetMapping
    public ResponseEntity<List<ResourceResponse>> getAllResources(
            @RequestParam(required = false) ResourceType type
    ) {
        var resources = getResourcesUseCase.execute(type)
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

    @PostMapping("/{id}/availability/reserve")
    public ResponseEntity<Void> reserveAvailability(@PathVariable Long id) {
        resourceAvailabilityUseCase.reserve(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{id}/availability/release")
    public ResponseEntity<Void> releaseAvailability(
            @PathVariable Long id,
            @RequestHeader("Idempotency-Key") @NotBlank @Size(max = 128) String operationId
    ) {
        resourceAvailabilityUseCase.release(id, operationId);
        return ResponseEntity.noContent().build();
    }
}
