package tenniscourts.exceptions;

import tenniscourts.entities.SystemEntity;

/**
 * @author Emma Sommerova
 */
public class InvalidEntityException extends RuntimeException {

    private SystemEntity entity;

    public InvalidEntityException(SystemEntity entity, String message) {
        super("Entity: " + entity + " is invalid\n" +
                "action " + message + " can't be performed");
        this.entity = entity;
    }

    public InvalidEntityException(String message) {
        super(message);
    }

    public SystemEntity getEntity() {
        return entity;
    }
}
