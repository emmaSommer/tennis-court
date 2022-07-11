package tenniscourts.exceptions;

import tenniscourts.entities.SystemEntity;

/**
 * @author Emma Sommerova
 */
public class InvalidEntityException extends RuntimeException{

    public InvalidEntityException(SystemEntity entity, String message) {
        super("Entity: " + String.valueOf(entity) + " is invalid\n" +
                "action " + message + " can't be performed");
    }

    public InvalidEntityException(String message) {
        super(message);
    }
}
