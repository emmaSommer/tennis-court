package tenniscourts.controllers;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import tenniscourts.entities.Client;
import tenniscourts.storage.ClientRepository;

/**
 * Controller for the Client class
 *
 * @author Emma Sommerova
 */

@RestController
public class ClientController extends EntityController<Client> {

    public static final String ROOT_NAME = "users";

    private final ClientRepository repository;

    /**
     * Constructor
     *
     * @param repository for Client entity
     */
    public ClientController(ClientRepository repository) {
        this.repository = repository;
    }

    @Override
    public ClientRepository getRepository() {
        return repository;
    }

    @Override
    public String getEntityName() {
        return Client.ENTITY_NAME;
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
     * @return REST model of the new Client instance
     */
    public EntityModel<Client> addEntity(String name, String phoneNumber) {
        Client entity = new Client(name, phoneNumber);
        return super.addEntity(entity);
    }

}
