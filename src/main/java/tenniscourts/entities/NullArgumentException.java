package tenniscourts.entities;

/**
 * Exception thrown while attempting to
 * initiate an entity with a null parameter
 *
 * @author Emma Sommerova
 */

public class NullArgumentException extends RuntimeException {

    NullArgumentException(String entityName, String arguments) {
        super("Could not initiate " + entityName + " with parameters: " + arguments);
    }
}
