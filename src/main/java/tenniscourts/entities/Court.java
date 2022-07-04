package tenniscourts.entities;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import tenniscourts.controllers.CourtController;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

/**
 * @author Emma Sommerova
 */

@Entity
public class Court extends SystemEntity<Court> {

    @Id
    @GeneratedValue
    private Long id;
    @ManyToOne
    private CourtType type;

    public Court(CourtType type) {
        this.type = type;
    }

    public Court(){
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
