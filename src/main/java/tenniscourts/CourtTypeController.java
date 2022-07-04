package tenniscourts;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Emma Sommerova
 */

@RestController
public class CourtTypeController extends EntityController<CourtType> {

    private final CourtTypeRepository repository;

    public CourtTypeController(CourtTypeRepository repository) {
        this.repository = repository;
    }


    @GetMapping("/court_types")
    @Override
    public CollectionModel<EntityModel<CourtType>> getAll() {
        return super.getAll(repository.findAll());
    }

    @GetMapping("/court_types/{id}")
    @Override
    public EntityModel<CourtType> getEntity(@PathVariable Long id) {
        return super.getEntity(id, repository.findById(id).orElseThrow());
    }

}
