package tenniscourts.storage;

import org.springframework.data.jpa.repository.JpaRepository;
import tenniscourts.entities.Client;

/**
 * @author Emma Sommerova
 */
public interface ClientRepository extends JpaRepository<Client, Long> {
}
