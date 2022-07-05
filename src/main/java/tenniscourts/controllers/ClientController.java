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
 * @author Emma Sommerova
 */

@RestController
public class ClientController extends EntityController<Client>{

    public static final String rootName = "users";

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


    public EntityModel<Client> addEntity(String name, String phoneNumber) {
        if (name == null || phoneNumber == null){
            throw new IllegalArgumentException("null argument");
        }

        Client entity = new Client(name, phoneNumber);
        return super.addEntity(entity);
    }

    @Override
    public CollectionModel<EntityModel<Client>> deleteEntity(Long id) {
        // todo check user doesn not have anz reservations
        return super.deleteEntity(id);
    }

    @Override
    public EntityModel<Client> updateEntity(Long id, Client newEntity) {
        // todo check newEntity is valid
        Optional<Client> oldEntity = super.getRepository().findById(id);
        if (oldEntity.isEmpty()){
            return super.addEntity(newEntity);
        }
        Client entity = oldEntity.orElseThrow();
        entity.setName(newEntity.getName());
        entity.setPhone_number(newEntity.getPhone_number());
        return super.updateEntity(id, newEntity);
    }
}
