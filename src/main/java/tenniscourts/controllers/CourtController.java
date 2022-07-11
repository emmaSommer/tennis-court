package tenniscourts.controllers;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import tenniscourts.entities.CourtType;
import tenniscourts.entities.Court;
import tenniscourts.entities.Reservation;
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

    public List<Court> getWithCourtType(Long id){
        List<Court> courts = repository.findAllByCourtType_Id(id);
        if (courts.isEmpty()){
            throw new EntityNotFoundException("Can't find courts with type id: " + id);
        }
        return courts;
    }

    /**
     * @param courtType of the new Court instance
     * @return REST model of the new instance
     */
    public Court addEntity(CourtType courtType) {
        Court court = new Court(courtType);
        return super.addEntity(court);
    }

    @Override
    public List<Court> deleteEntity(Long id) {
        try {
            reservationController.getWithCourtId(id);
            throw new IllegalArgumentException("Cant delete court with reservations");
        } catch (EntityNotFoundException e){
            return super.deleteEntity(id);

        }
    }
}