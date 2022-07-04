package tenniscourts;

import org.springframework.hateoas.EntityModel;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.math.BigDecimal;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

/**
 * @author Emma Sommerova
 */

@Entity
public class CourtType extends SystemEntity<CourtType>{

    @Id
    @GeneratedValue
    private Long id;
    private String name;
    // price per hour
    // TODO - reconsider datatype
    private BigDecimal price;


    public CourtType(String name, BigDecimal price) {
        this.name = name;
        this.price = price;
    }

    public CourtType(){
        this.name = "default";
        this.price = BigDecimal.ZERO;
    }


    static public CourtType getDefault(){
        return new CourtType("default", BigDecimal.ZERO);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Long getId() {
        return id;
    }

    @Override
    public EntityModel<CourtType> toModel() {
        return EntityModel.of(
                this,
                linkTo(methodOn(CourtTypeController.class).getEntity(getId())).withSelfRel(),
                linkTo(methodOn(CourtTypeController.class).getAll()).withRel("court_types")
        );
    }


}
