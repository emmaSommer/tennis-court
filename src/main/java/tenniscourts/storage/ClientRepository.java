package tenniscourts.storage;

import org.springframework.data.jpa.repository.JpaRepository;
import tenniscourts.entities.Client;

import java.util.List;

/**
 * @author Emma Sommerova
 */
public interface ClientRepository extends JpaRepository<Client, Long> {

    List<Client> findByPhoneNumber(String phoneNumber);
}
