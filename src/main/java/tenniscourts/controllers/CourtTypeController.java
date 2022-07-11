package tenniscourts.controllers;

import org.springframework.web.bind.annotation.RestController;
import tenniscourts.entities.Court;
import tenniscourts.entities.CourtType;
import tenniscourts.exceptions.EntityNotFoundException;
import tenniscourts.exceptions.InvalidDeleteException;
import tenniscourts.storage.CourtTypeRepository;

import java.math.BigDecimal;
import java.util.List;

/**
 * Entity controller for CourtType class
 *
 * @author Emma Sommerova
 */

@RestController
public class CourtTypeController extends EntityController<CourtType> {

    public static final String ROOT_NAME = "court_types";

    private final CourtTypeRepository repository;
    private final CourtController courtController;

    /**
     * Constructor
     *
     * @param repository for the CourtType entity
     */
    public CourtTypeController(CourtTypeRepository repository,
                               CourtController courtController) {
        this.repository = repository;
        this.courtController = courtController;
    }

    @Override
    public CourtTypeRepository getRepository() {
        return repository;
    }

    @Override
    public String getEntityName() {
        return CourtType.ENTITY_NAME;
    }

    @Override
    public String getRootName() {
        return ROOT_NAME;
    }

    /**
     * @param name  of the new type of court
     * @param price for a reservation for one hour
     * @return new CourtType instance
     */
    public CourtType addEntity(String name, BigDecimal price) {
        CourtType courtType = new CourtType(name, price);
        return super.addEntity(courtType);
    }

    @Override
    public CourtType deleteEntity(Long id) {
        try {
            List<Court> courts = courtController.getWithCourtType(id);
            throw new InvalidDeleteException("Deleting court type with existing courts\n\t" + courts);
        } catch (EntityNotFoundException e) {
            return super.deleteEntity(id);
        }
    }
}
