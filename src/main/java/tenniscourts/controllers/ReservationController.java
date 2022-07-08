package tenniscourts.controllers;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import tenniscourts.entities.Reservation;

/**
 * Entity controller for the Reservation class
 *
 * @author Emma Sommerova
 */

@RestController
public class ReservationController extends EntityController<Reservation> {

    public static final String ROOT_NAME = "reservations";

    /**
     * Constructor
     *
     * @param repository for the Reservation entity
     */
    public ReservationController(JpaRepository<Reservation, Long> repository) {
        super(repository);
    }

    @Override
    public String getEntityName() {
        return Reservation.ENTITY_NAME;
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
        return super.updateEntity(id, newEntity);
    }
}
