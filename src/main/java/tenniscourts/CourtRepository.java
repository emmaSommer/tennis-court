package tenniscourts;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Emma Sommerova
 */
public interface CourtRepository extends JpaRepository<Court, Long> {
}
