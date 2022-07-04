package tenniscourts.controllers;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import tenniscourts.entities.Reservation;

import java.util.Optional;

/**
 * @author Emma Sommerova
 */

@RestController
public class ReservationController extends EntityController<Reservation> {

    public ReservationController(JpaRepository<Reservation, Long> repository) {
        super(repository);
    }

    @Override
    public String getRootName() {
        return "/reservations";
    }

    @GetMapping("/reservations/{id}")
    @Override
    public EntityModel<Reservation> getEntityModel(@PathVariable Long id) {
        return super.getEntityModel(id);
    }

    @GetMapping("/reservations")
    @Override
    public CollectionModel<EntityModel<Reservation>> getAll() {
        return super.getAll();
    }

    @Override
    public EntityModel<Reservation> addEntity(Reservation entity) {
        // todo - change signature
        return super.addEntity(entity);
    }

    @Override
    public EntityModel<Reservation> updateEntity(Long id, Reservation newEntity) {
        Optional<Reservation> oldEntity = super.getRepository().findById(id);
        if (oldEntity.isEmpty()){
            return super.addEntity(newEntity);
        }
        Reservation entity = oldEntity.orElseThrow();
        entity.setStartDate(newEntity.getStartDate());
        entity.setEndDate(newEntity.getEndDate());
        entity.setClient(newEntity.getClient());
        entity.setCourt(newEntity.getCourt());
        return super.updateEntity(id, newEntity);
    }
}
