package tenniscourts.exceptions;

/**
 * @author Emma Sommerova
 */
public class InvalidRequestException extends RuntimeException {

    public InvalidRequestException(String message) {
        super(message);
    }
}
