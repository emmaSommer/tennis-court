package tenniscourts.storage;

import org.springframework.data.jpa.repository.JpaRepository;
import tenniscourts.entities.Court;

/**
 * @author Emma Sommerova
 */
public interface CourtRepository extends JpaRepository<Court, Long> {
}
