package tenniscourts.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

/**
 * @author Emma Sommerova
 */

@Entity
public class Court extends SystemEntity {

    @Id
    @GeneratedValue
    private Long id;
    @ManyToOne
    private CourtType type;

    public Court(CourtType type) {
        this.type = type;
    }

    public Court() {
        this.type = CourtType.getDefault();
    }


    public Long getId() {
        return id;
    }

    public CourtType getType() {
        return type;
    }

    public void setType(CourtType type) {
        this.type = type;
    }

    public void setId(Long id) {
        this.id = id;
    }

}