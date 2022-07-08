package tenniscourts.entities;

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
    @Column(unique = true)
    private String phoneNumber;

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
        this.name = name;
        this.phoneNumber = phone_number;
        if (!this.isValid()){
            throw new IllegalStateException();
        }
    }

    /**
     * Default constructor
     */
    public Client() {
        this.name = "default user";
        this.phoneNumber = "";
    }

    @Override
    public boolean isValid() {
        return name != null && !name.isBlank()
                && validNumber(phoneNumber);
    }

    @Override
    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    @Override
    public String getRootName() {
        return ClientController.ROOT_NAME;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    @Override
    public void cloneAttributes(SystemEntity newEntity) {
        if (newEntity.getClass() != Client.class) {
            throw new IllegalArgumentException();
        }
        Client newClient = (Client) newEntity;
        this.name = newClient.getName();
        this.phoneNumber = newClient.getPhoneNumber();

    }

    @Override
    public String toString() {
        return "Client{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", phone_number='" + phoneNumber + '\'' +
                '}';
    }

}
