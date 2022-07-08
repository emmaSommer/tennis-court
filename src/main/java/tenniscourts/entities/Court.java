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

    public static final String ENTITY_NAME = "court";

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
        this.type = type;
        if (!this.isValid()) {
            throw new IllegalStateException();
        }
    }

    /**
     * Default constructor
     */
    public Court() {
        this.type = new CourtType();
    }

    @Override
    public boolean isValid() {
        return type != null && type.isValid();
    }

    public Long getId() {
        return id;
    }

    public CourtType getType() {
        return type;
    }

    @Override
    public String getRootName() {
        return CourtController.ROOT_NAME;
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
