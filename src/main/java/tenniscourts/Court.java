package tenniscourts;

import org.springframework.hateoas.EntityModel;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import java.math.BigDecimal;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

/**
 * @author Emma Sommerova
 */

@Entity
public class Court extends SystemEntity<Court>{

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

    @Override
    public EntityModel<Court> toModel() {
        return EntityModel.of(
                this,
                linkTo(methodOn(CourtController.class).getEntity(getId())).withSelfRel(),
                linkTo(methodOn(CourtController.class).getAll()).withRel("courts")
        );
    }
}
