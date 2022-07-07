package tenniscourts.entities;

import tenniscourts.controllers.CourtController;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

/**
 * Class for representing individual courts
 *
 * @author Emma Sommerova
 */

@Entity
public class Court extends SystemEntity {

    public static final String entityName = "court";

    @Id
    @GeneratedValue
    private Long id;
    @ManyToOne
    private CourtType type;


    /**
     * Constructor
     *
     * @param type of the court
     */
    public Court(CourtType type) {
        if (type == null) {
            throw new NullArgumentException(entityName, "court type = null");
        }
        this.type = type;
    }

    /**
     * Default constructor
     */
    public Court() {
        this.type = new CourtType();
    }


    public Long getId() {
        return id;
    }

    public CourtType getType() {
        return type;
    }

    @Override
    public String getRootName() {
        return CourtController.rootName;
    }

    public void setType(CourtType type) {
        this.type = type;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public void cloneAttributes(SystemEntity newEntity) {
        if (newEntity.getClass() != Court.class) {
            throw new IllegalArgumentException();
        }
        Court newCourt = (Court) newEntity;
        this.type = newCourt.getType();
    }

    @Override
    public String toString() {
        return "Court{" +
                "id=" + id +
                ", type=" + type +
                '}';
    }
}
