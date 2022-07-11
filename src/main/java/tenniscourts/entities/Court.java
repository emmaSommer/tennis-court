package tenniscourts.entities;

import com.sun.istack.NotNull;
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

    @ManyToOne
    @NotNull
    private CourtType courtType;


    /**
     * Constructor
     *
     * @param type of the court
     */
    public Court(CourtType type) {
        this.courtType = type;
    }

    /**
     * Default constructor
     */
    public Court() {

    }

    @Override
    public boolean isValid() {
        return courtType != null && courtType.isValid();
    }

    public CourtType getCourtType() {
        return courtType;
    }

    public void setCourtType(CourtType courtType) {
        if (courtType == null) {
            throw new IllegalArgumentException();
        }
        this.courtType = courtType;
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
        this.courtType = newCourt.getCourtType();
    }

    @Override
    public String toString() {
        return "Court{" +
                "id=" + id +
                ", type=" + courtType +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (!super.equals(o)) {
            return false;
        }
        Court court = (Court) o;
        return Objects.equals(courtType, court.courtType);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, courtType);
    }
}
