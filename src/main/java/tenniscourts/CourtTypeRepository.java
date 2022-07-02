package tenniscourts;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Emma Sommerova
 */
public interface CourtTypeRepository extends JpaRepository<CourtType, Long> {
}
