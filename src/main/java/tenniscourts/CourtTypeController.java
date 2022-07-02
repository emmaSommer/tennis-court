package tenniscourts;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Emma Sommerova
 */

@RestController
public class CourtTypeController {

    private final CourtTypeRepository repository;

    public CourtTypeController(CourtTypeRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/court_types")
    public String getCourtTypes(){
        return repository.findAll().toString();
    }
}
