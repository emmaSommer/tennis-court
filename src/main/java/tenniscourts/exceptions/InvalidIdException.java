package tenniscourts.exceptions;

/**
 * @author Emma Sommerova
 */
public class InvalidIdException extends RuntimeException{

    public InvalidIdException(Long id, String entityName) {
        super(id + " ");
    }

    public InvalidIdException(String message) {
        super(message);
    }
}
