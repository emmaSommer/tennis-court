package tenniscourts.entities;

import tenniscourts.controllers.CourtController;

import javax.persistence.*;
import java.util.Objects;

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
    }

    /**
     * Default constructor
     */
    public Court() {

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Court court = (Court) o;
        return Objects.equals(id, court.id) && Objects.equals(type, court.type);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, type);
    }
}
