package tenniscourts.exceptions;

/**
 * @author Emma Sommerova
 */
public class InvalidIdException extends RuntimeException {

    public InvalidIdException(String message) {
        super(message);
    }
}
