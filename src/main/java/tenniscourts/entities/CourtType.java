package tenniscourts.entities;

import tenniscourts.controllers.CourtTypeController;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.math.BigDecimal;

/**
 * Class for representing specific types of courts
 *
 * @author Emma Sommerova
 */

@Entity
public class CourtType extends SystemEntity {

    public static final String entityName = "court_type";

    @Id
    @GeneratedValue
    private Long id;
    private String name;
    private BigDecimal price;


    /**
     * Constructor
     *
     * @param name  of the court type
     * @param price per one hour
     */
    public CourtType(String name, BigDecimal price) {
        if (name == null || price == null) {
            throw new NullArgumentException(entityName, "name: " + name + ", price: " + price);
        }
        this.name = name;
        this.price = price;
    }

    /**
     * Default constructor
     */
    public CourtType() {
        this.name = "default";
        this.price = BigDecimal.ZERO;
    }


    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    @Override
    public String getRootName() {
        return CourtTypeController.rootName;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "CourtType{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", price=" + price +
                '}';
    }
}
