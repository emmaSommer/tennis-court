package tenniscourts.exceptions;

/**
 * @author Emma Sommerova
 */
public class InvalidDeleteException extends RuntimeException{
    public InvalidDeleteException(String message) {
        super(message);
    }

    public InvalidDeleteException(Long id, String repoName){
        super("Id: " + id + " doesn't exist in " + repoName);
    }
}
