package tenniscourts.storage;

import org.springframework.data.jpa.repository.JpaRepository;
import tenniscourts.entities.Reservation;

/**
 * @author Emma Sommerova
 */
public interface ReservationRepository extends JpaRepository<Reservation, Long> {
}
