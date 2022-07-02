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
public class CourtController {

    private final CourtRepository repository;

    CourtController(CourtRepository repository) {
        this.repository = repository;
    }


    // Aggregate root
    // tag::get-aggregate-root[]
    @GetMapping("/Courts")
    String all() {
        List<Court> Courts = repository.
                findAll();
        return Courts.toString();
    }
    // end::get-aggregate-root[]

    @PostMapping("/Courts")
    Court newCourt(@RequestBody Court newCourt) {
        return repository.save(newCourt);
    }

    // Single item

    @GetMapping("/Courts/{id}")
    String one(@PathVariable Long id) throws Exception {

        Court Court = repository.findById(id) //
                .orElseThrow(() -> new Exception(String.valueOf(id)));

        return Court.toString();
    }

    @PutMapping("/Courts/{id}")
    Court replaceCourt(@RequestBody Court newCourt, @PathVariable Long id) {

        return repository.findById(id)

                .orElseGet(() -> {
                    newCourt.setId(id);
                    return repository.save(newCourt);
                });
    }

    @DeleteMapping("/Courts/{id}")
    void deleteCourt(@PathVariable Long id) {
        repository.deleteById(id);
    }
}