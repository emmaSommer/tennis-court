package tenniscourts.controllers;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import tenniscourts.entities.Client;
import tenniscourts.entities.Court;
import tenniscourts.entities.PlayType;
import tenniscourts.entities.Reservation;
import tenniscourts.storage.ReservationRepository;

import java.time.LocalDateTime;

/**
 * Entity controller for the Reservation class
 *
 * @author Emma Sommerova
 */

@RestController
public class ReservationController extends EntityController<Reservation> {

    public static final String ROOT_NAME = "reservations";

    private final ReservationRepository repository;

    /**
     * Constructor
     *
     * @param repository for the Reservation entity
     */
    public ReservationController(ReservationRepository repository) {
        this.repository = repository;
    }

    @Override
    public ReservationRepository getRepository() {
        return repository;
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

    public EntityModel<Reservation> addEntity(LocalDateTime start, LocalDateTime end,
                                              Court court, PlayType playType,
                                              Client client) {
        Reservation reservation = new Reservation(start, end, court, client, playType);
        return super.addEntity(reservation);
    }
}
