package tenniscourts.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import tenniscourts.entities.Client;
import tenniscourts.storage.ClientRepository;

import java.util.List;

/**
 * Controller for the Client class
 *
 * @author Emma Sommerova
 */

@RestController
public class ClientController extends EntityController<Client> {

    public static final String ROOT_NAME = "users";

    private final ClientRepository repository;
    private final ReservationController reservationController;

    /**
     * Constructor
     *
     * @param repository for Client entity
     */
    public ClientController(ClientRepository repository,
                            ReservationController reservationController) {
        this.repository = repository;
        this.reservationController = reservationController;
    }

    @Override
    public ClientRepository getRepository() {
        return repository;
    }

    @Override
    public String getEntityName() {
        return Client.ENTITY_NAME;
    }

    @Override
    public String getRootName() {
        return ROOT_NAME;
    }

    public Client getByPhoneNumber(String phoneNumber) {
        if (!Client.validNumber(phoneNumber)) {
            throw new IllegalArgumentException("Can not find client with invalid number: " + phoneNumber);
        }
        List<Client> clients = repository.findByPhoneNumber(phoneNumber);
        if (clients.isEmpty()) {
            throw new EntityNotFoundException("Client with number: " + phoneNumber + " is not in repository");
        }
        if (clients.size() > 1) {
            throw new IllegalStateException("Duplicate number in database: " + phoneNumber);
        }
        return clients.get(0);
    }


    /**
     * Creates a new Client with the given parameters and
     * saves it into the repository
     *
     * @param name        of the Client
     * @param phoneNumber of the Client
     * @return REST model of the new Client instance
     */
    public Client addEntity(String name, String phoneNumber) {
        Client entity = new Client(name, phoneNumber);
        return super.addEntity(entity);
    }

    @Override
    public List<Client> deleteEntity(Long id) {
        try {
            reservationController.getWithClientId(id);
            throw new IllegalArgumentException("Can't delete client with reservations");

        } catch (EntityNotFoundException e){
            return super.deleteEntity(id);
        }
    }
}
