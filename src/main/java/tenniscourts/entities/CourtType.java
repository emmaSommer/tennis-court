package tenniscourts.entities;

import tenniscourts.controllers.ClientController;
import tenniscourts.controllers.CourtTypeController;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.math.BigDecimal;

/**
 * @author Emma Sommerova
 */

@Entity
public class CourtType extends SystemEntity {

    public static final String entityName = "court_type";
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

    @Override
    public String getRootName() {
        return CourtTypeController.rootName;
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

}
