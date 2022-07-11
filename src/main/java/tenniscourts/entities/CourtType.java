package tenniscourts.entities;

import com.sun.istack.NotNull;
import tenniscourts.controllers.CourtTypeController;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.math.BigDecimal;
import java.util.Objects;

/**
 * Class for representing specific types of courts
 *
 * @author Emma Sommerova
 */

@Entity
public class CourtType extends SystemEntity {

    public static final String ENTITY_NAME = "court_type";

    @Id
    @GeneratedValue
    private Long id;
    @NotNull
    private String name;
    @NotNull
    private BigDecimal price;


    /**
     * Constructor
     *
     * @param name  of the court type
     * @param price per one minute
     */
    public CourtType(String name, BigDecimal price) {

        this.name = name;
        this.price = price;
    }

    /**
     * Default constructor
     */
    public CourtType() {
    }

    @Override
    public boolean isValid() {
        return name != null && !name.isBlank()
                && price != null;
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
        return CourtTypeController.ROOT_NAME;
    }

    public void setName(String name) {
        if (name == null){
            throw new IllegalArgumentException();
        }
        this.name = name;
    }

    public void setPrice(BigDecimal price) {
        if (price == null){
            throw new IllegalArgumentException();
        }
        this.price = price;
    }

    @Override
    public void cloneAttributes(SystemEntity newEntity) {
        if (newEntity.getClass() != CourtType.class) {
            throw new IllegalArgumentException();
        }
        CourtType newType = (CourtType) newEntity;
        this.name = newType.getName();
        this.price = newType.getPrice();
    }

    @Override
    public String toString() {
        return "CourtType{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", price=" + price +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (!super.equals(o)){
            return false;
        }
        CourtType courtType = (CourtType) o;
        return Objects.equals(id, courtType.id)
                && Objects.equals(name, courtType.name)
                && price.compareTo(courtType.price) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, price);
    }
}
