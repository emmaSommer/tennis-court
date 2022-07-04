package tenniscourts;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

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
    public CollectionModel<EntityModel<CourtType>> findAll(){
        return super.findAll();
    }

    @GetMapping("/court_types/{id}")
    @Override
    public EntityModel<CourtType> findById(@PathVariable Long id){
        return super.findById(id);
    }


    @Override
    public List<CourtType> getAll() {
        return repository.findAll();
    }

    @Override
    public Optional<CourtType> getEntity(Long id) {
        return repository.findById(id);
    }
}
