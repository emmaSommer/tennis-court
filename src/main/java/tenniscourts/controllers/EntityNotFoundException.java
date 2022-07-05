package tenniscourts.controllers;

import tenniscourts.entities.SystemEntity;

/**
 * @author Emma Sommerova
 */
public class EntityNotFoundException extends RuntimeException{

    public EntityNotFoundException(String entityName, Long id) {
        super("Could not find " + entityName +
                " with id: " + id);
    }
}
