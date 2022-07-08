package tenniscourts.entities;

import antlr.StringUtils.*;
import tenniscourts.controllers.ClientController;

import java.util.regex.Pattern;
import javax.persistence.*;

/**
 * Class for representing clients that have
 * reservations in the system
 *
 * @author Emma Sommerova
 */
@Entity
public class Client extends SystemEntity {

    public static final String ENTITY_NAME = "client";

    @Id
    @GeneratedValue
    private Long id;
    private String name;
    // todo reconsider number datatype
    @Column(unique = true)
    private String phone_number;

    public static boolean validNumber(String phone_number) {
        return phone_number.length() == 12 &&
                Pattern.compile("^[0-9]+$").matcher(phone_number).matches();
    }


    /**
     * Constructor
     *
     * @param name         of the client
     * @param phone_number of the client
     */
    public Client(String name, String phone_number) {
        if (name == null || phone_number == null) {
            throw new NullArgumentException(
                    ENTITY_NAME,
                    "name = " + name + ", phone number: " + phone_number);
        }
        if (!validNumber(phone_number)) {
            throw new IllegalArgumentException("Phone number: " + phone_number + " has wrong fromat");
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
    public boolean isValid() {
        return name != null && ! name.isBlank()
                && validNumber(phone_number);
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
        return ClientController.ROOT_NAME;
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
