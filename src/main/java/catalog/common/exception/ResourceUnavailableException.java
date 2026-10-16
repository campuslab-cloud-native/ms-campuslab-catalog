package catalog.common.exception;

public class ResourceUnavailableException extends RuntimeException {

    public ResourceUnavailableException(Long id) {
        super("No hay cupos disponibles para el recurso " + id);
    }
}
