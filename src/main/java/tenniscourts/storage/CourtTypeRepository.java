package tenniscourts.storage;

import org.springframework.data.jpa.repository.JpaRepository;
import tenniscourts.entities.CourtType;

/**
 * @author Emma Sommerova
 */
public interface CourtTypeRepository extends JpaRepository<CourtType, Long> {
}
