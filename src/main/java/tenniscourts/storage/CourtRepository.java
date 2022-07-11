package tenniscourts.storage;

import org.springframework.data.jpa.repository.JpaRepository;
import tenniscourts.entities.Court;

import java.util.List;

/**
 * @author Emma Sommerova
 */
public interface CourtRepository extends JpaRepository<Court, Long> {
    List<Court> findAllByCourtType_Id(Long courtTypeId);
}
