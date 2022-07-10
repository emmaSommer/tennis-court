package tenniscourts;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.event.annotation.BeforeTestClass;
import tenniscourts.controllers.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
/**
 *
 * @author Emma Sommerova
 */

@SpringBootTest
class DataLoaderTest {

    @Autowired
    private ClientController clientController;
    @Autowired
    private CourtController courtController;
    @Autowired
    private CourtTypeController courtTypeController;
    @Autowired
    private ReservationController reservationController;

    @Test
    void loadData() {
        DataLoader loader = new DataLoader();
       // loader.loadData(courtController.getRepository(),
       //         courtTypeController.getRepository(),
       //         clientController.getRepository(),
       //         reservationController.getRepository());

        assertThat(courtController.getRepository().count()).isEqualTo(3);
        assertThat(courtTypeController.getRepository().count()).isEqualTo(3);
        assertThat(clientController.getRepository().count()).isEqualTo(2);
        assertThat(reservationController.getRepository().count()).isEqualTo(6);

    }
}