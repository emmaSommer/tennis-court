package tenniscourts.entities;

import tenniscourts.controllers.ClientController;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * Class for representing clients that have
 * reservations in the system
 *
 * @author Emma Sommerova
 */
@Entity
public class Client extends SystemEntity {

    public static final String entityName = "client";

    @Id
    @GeneratedValue
    private Long id;
    private String name;
    // todo reconsider number datatype
    private String phone_number;


    /**
     * Constructor
     *
     * @param name         of the client
     * @param phone_number of the client
     */
    public Client(String name, String phone_number) {
        // todo check phone number validity

        if (name == null || phone_number == null) {
            throw new NullArgumentException(
                    entityName,
                    "name = " + name + ", phone number: " + phone_number);
        }

        this.name = name;
        this.phone_number = phone_number;
    }

    /**
     * Default constructor
     */
    public Client() {
        this.name = "default user";
        this.phone_number = "";
    }


    @Override
    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPhone_number() {
        return phone_number;
    }

    @Override
    public String getRootName() {
        return ClientController.rootName;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }

    @Override
    public void cloneAttributes(SystemEntity newEntity) {
        if (newEntity.getClass() != Client.class) {
            throw new IllegalArgumentException();
        }
        Client newClient = (Client) newEntity;
        this.name = newClient.getName();
        this.phone_number = newClient.getPhone_number();

    }

    @Override
    public String toString() {
        return "Client{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", phone_number='" + phone_number + '\'' +
                '}';
    }
}
