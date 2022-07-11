package tenniscourts.exceptions;


/**
 * Exception thrown when an entity with the given
 * id is not in the repository
 *
 * @author Emma Sommerova
 */
public class EntityNotFoundException extends RuntimeException {

    private String entityName;
    private Long id;
    private String action;

    public EntityNotFoundException(String entityName, Long id, String action) {
        super("Could not find " + entityName +
                " with id: " + id + "\n" +
                "can't perform action: " + action);
        this.entityName = entityName;
        this.id = id;
        this.action = action;
    }

    public EntityNotFoundException(String message){
        super("Could not find any " + message);
    }

    public String getEntityName() {
        return entityName;
    }

    public Long getId() {
        return id;
    }

    public String getAction() {
        return action;
    }
}
