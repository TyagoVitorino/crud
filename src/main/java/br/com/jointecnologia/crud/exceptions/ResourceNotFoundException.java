package br.com.jointecnologia.crud.exceptions;

public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(String resource, Long id) {
        super(String.format("%s with ID %d not found", resource, id));
    }
}
