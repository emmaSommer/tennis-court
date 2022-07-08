package tenniscourts.storage;

import org.springframework.data.jpa.repository.JpaRepository;
import tenniscourts.entities.Reservation;

import java.util.List;

/**
 * @author Emma Sommerova
 */
public interface ReservationRepository extends JpaRepository<Reservation, Long> {

    List<Reservation> findAllByCourt_Id(Long courtId);
}
