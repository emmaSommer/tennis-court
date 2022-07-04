package tenniscourts;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

/**
 * @author Emma Sommerova
 */

@RestController
public class CourtController extends EntityController<Court> {

    private final CourtRepository repository;

    CourtController(CourtRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/courts")
    @Override
    public CollectionModel<EntityModel<Court>> getAll() {
        return super.getAll(repository.findAll());
    }

    @GetMapping("/courts/{id}")
    @Override
    public EntityModel<Court> getEntity(@PathVariable Long id) {
        return super.getEntity(id, repository.findById(id).orElseThrow());
    }
}