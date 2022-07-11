package tenniscourts.controllers;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import tenniscourts.entities.Reservation;
import tenniscourts.exceptions.EntityNotFoundException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

/**
 * @author Emma Sommerova
 */

@SpringBootTest
class EntityControllerTest {

    @Autowired
    private ClientController clientController;
    @Autowired
    private CourtController courtController;
    @Autowired
    private CourtTypeController courtTypeController;
    @Autowired
    private ReservationController reservationController;


    @Test
    void getAll() {
        List<Reservation> reservations = reservationController.getAll();
        assertEquals(6, reservations.size());

        reservationController.getRepository().deleteAll();
        assertThrows(EntityNotFoundException.class,
                () -> reservationController.getAll());

        reservationController.getRepository().saveAll(reservations);
    }

    @Test
    void getEntity(){
        assertThrows(IllegalArgumentException.class, () -> reservationController.getEntity(null));
    }


    @Test
    void deleteEntity() {
    }

    @Test
    void updateEntity() {
    }


    @Test
    void getCollectionModel() {
    }

}