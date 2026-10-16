package catalog.common.exception;

public class AvailabilityOperationConflictException extends RuntimeException {

    public AvailabilityOperationConflictException(String operationId) {
        super("La operación de liberación " + operationId
                + " ya fue utilizada para otro recurso");
    }
}
