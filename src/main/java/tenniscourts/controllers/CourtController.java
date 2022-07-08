package tenniscourts.controllers;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import tenniscourts.entities.CourtType;
import tenniscourts.entities.Court;
import tenniscourts.storage.CourtRepository;


/**
 * Entity controller for the Court class
 *
 * @author Emma Sommerova
 */

@RestController
public class CourtController extends EntityController<Court> {

    public static final String ROOT_NAME = "courts";

    private final CourtRepository repository;
    /**
     * Constructor
     *
     * @param repository for Court entity
     */
    CourtController(CourtRepository repository) {
        this.repository = repository;
    }

    @Override
    public CourtRepository getRepository() {
        return repository;
    }

    @Override
    public String getEntityName() {
        return Court.ENTITY_NAME;
    }

    @GetMapping("/courts/{id}")
    @Override
    public EntityModel<Court> getEntityModel(@PathVariable Long id) {
        return super.getEntityModel(id);
    }

    /**
     * @param courtType of the new Court instance
     * @return REST model of the new instance
     */
    public EntityModel<Court> addEntity(CourtType courtType) {
        Court court = new Court(courtType);
        return super.addEntity(court);
    }
}