package catalog.domain.useCase;

public interface ResourceAvailabilityUseCase {

    void reserve(Long id);

    void release(Long id, String operationId);
}
