package tenniscourts.controllers;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import tenniscourts.entities.Client;

import java.util.Optional;

/**
 * Controller for the Client class
 *
 * @author Emma Sommerova
 */

@RestController
public class ClientController extends EntityController<Client> {

    public static final String rootName = "users";

    /**
     * Constructor
     *
     * @param repository for Client entity
     */
    public ClientController(JpaRepository<Client, Long> repository) {
        super(repository);
    }

    @Override
    public String getEntityName() {
        return Client.entityName;
    }

    @GetMapping("/users/{id}")
    @Override
    public EntityModel<Client> getEntityModel(@PathVariable Long id) {
        return super.getEntityModel(id);
    }

    @GetMapping("/users")
    @Override
    public CollectionModel<EntityModel<Client>> getAll() {
        return super.getAll();
    }


    /**
     * Creates a new Client with the given parameters and
     * saves it into the repository
     *
     * @param name        of the Client
     * @param phoneNumber of the Client
     * @return REST model of the new Client instatnce
     */
    public EntityModel<Client> addEntity(String name, String phoneNumber) {
        // todo check if number is valid
        if (name == null || phoneNumber == null) {
            // todo reconsider duplicate exception here and in class constructors
            throw new IllegalArgumentException("null argument");
        }

        Client entity = new Client(name, phoneNumber);
        return super.addEntity(entity);
    }

    /**
     * @param id of the entity to be deleted
     * @return REST model of the Client collection
     * @throws IllegalArgumentException if the client can not
     *                                  be deleted because of existing reservations
     *                                  -- todo : instead of deleting client could be replaced with default "Non active user"?
     *                                  -> only for clients without active reservation (past resrv. info)
     */
    @Override
    public CollectionModel<EntityModel<Client>> deleteEntity(Long id) {
        // todo check user doesn not have anz reservations
        return super.deleteEntity(id);
    }

    @Override
    public EntityModel<Client> updateEntity(Long id, Client newEntity) {
        // todo check newEntity is valid
        return super.updateEntity(id, newEntity);
    }
}
