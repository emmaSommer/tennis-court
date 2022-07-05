package tenniscourts.entities;

import tenniscourts.controllers.ClientController;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * @author Emma Sommerova
 */

@Entity
public class Client extends SystemEntity{

    public static final String entityName = "client";
    @Id
    @GeneratedValue
    private Long id;
    private String name;
    // todo reconsider number datatype
    private String phone_number;

    public Client(String name, String phone_number) {
        // todo check phone number validity
        this.name = name;
        this.phone_number = phone_number;
    }
     public Client(){
        this.name = "default user";
        this.phone_number = "";
     }

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public String getRootName() {
        return ClientController.rootName;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }
}
