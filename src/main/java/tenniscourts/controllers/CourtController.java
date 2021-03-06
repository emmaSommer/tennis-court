package tenniscourts.controllers;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import tenniscourts.entities.CourtType;
import tenniscourts.entities.Court;
import tenniscourts.entities.Reservation;
import tenniscourts.exceptions.EntityNotFoundException;
import tenniscourts.exceptions.InvalidDeleteException;
import tenniscourts.storage.CourtRepository;

import java.util.List;


/**
 * Entity controller for the Court class
 *
 * @author Emma Sommerova
 */

@RestController
public class CourtController extends EntityController<Court> {

    public static final String ROOT_NAME = "courts";

    private final CourtRepository repository;
    private final ReservationController reservationController;

    /**
     * Constructor
     *
     * @param repository for Court entity
     */
    CourtController(CourtRepository repository,
                    ReservationController reservationController) {
        this.repository = repository;
        this.reservationController = reservationController;
    }

    @Override
    public CourtRepository getRepository() {
        return repository;
    }

    @Override
    public String getEntityName() {
        return Court.ENTITY_NAME;
    }

    @Override
    public String getRootName() {
        return ROOT_NAME;
    }

    /**
     * @param id courtType id
     * @return list of courts with the given court type
     */
    public List<Court> getWithCourtType(Long id) {
        List<Court> courts = repository.findAllByCourtType_Id(id);
        if (courts.isEmpty()) {
            throw new EntityNotFoundException(getRootName() + " with court type id: '" + id + "'");
        }
        return courts;
    }

    /**
     * @param courtType of the new Court instance
     * @return new instance of Court saved into the repository
     */
    public Court addEntity(CourtType courtType) {
        Court court = new Court(courtType);
        return super.addEntity(court);
    }

    @Override
    public Court deleteEntity(Long id) {
        try {
            List<Reservation> r = reservationController.getWithCourtId(id);
            throw new InvalidDeleteException("Deleting courts with existing reservations\n\t" + r);

        } catch (EntityNotFoundException e) {
            return super.deleteEntity(id);
        }
    }

    @GetMapping("/" + ROOT_NAME + "/{id}")
    @Override
    public EntityModel<Court> getEntityModel(@PathVariable Long id) {
        return super.getEntityModel(id);
    }

    @GetMapping("/" + ROOT_NAME)
    @Override
    public CollectionModel<EntityModel<Court>> getCollectionModel() {
        return super.getCollectionModel();
    }
}