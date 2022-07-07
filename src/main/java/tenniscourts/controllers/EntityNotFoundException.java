package tenniscourts.controllers;


/**
 * Exception thrown when an entity with the given
 * id is not in the repository
 *
 * @author Emma Sommerova
 */
public class EntityNotFoundException extends RuntimeException {

    public EntityNotFoundException(String entityName, Long id) {
        super("Could not find " + entityName +
                " with id: " + id);
    }

    public EntityNotFoundException(String message){
        super(message);
    }
}
