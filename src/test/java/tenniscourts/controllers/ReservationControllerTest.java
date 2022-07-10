package tenniscourts.controllers;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import tenniscourts.entities.*;
import tenniscourts.storage.ReservationRepository;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Emma Sommerova
 */

@SpringBootTest
class ReservationControllerTest {

    @Autowired
    private ClientController clientController;
    @Autowired
    private CourtController courtController;
    @Autowired
    private CourtTypeController courtTypeController;
    @Autowired
    private ReservationController controller;

    private Reservation reservation;
    private Client client;
    private Court court;
    private CourtType courtType;

    LocalDateTime time = LocalDateTime.of(2020, 12, 26, 6, 5, 5);


    @BeforeEach
    public void setUp() {
        client = clientController.addEntity(ClientTest.VALID_CLIENT).getContent();
        courtType = courtTypeController.addEntity(CourtTypeTest.VALID_COURT_TYPE).getContent();
        court = courtController.addEntity(new Court(courtType)).getContent();
        reservation = controller.getRepository().save(new Reservation(
                time,
                time.plusHours(1),
                court, client, PlayType.SINGLES_PLAY));
    }

    @AfterEach
    public void tearDown() {
        controller.getRepository().delete(reservation);
        clientController.deleteEntity(client.getId());
        courtController.deleteEntity(court.getId());
        courtTypeController.deleteEntity(courtType.getId());
    }

    @Test
    void getEntityName() {
        assertEquals(Reservation.ENTITY_NAME, controller.getEntityName());
    }

    @Test
    void getEntityModel() {
        assertEquals(reservation, controller.getEntityModel(reservation.getId()).getContent());

        assertThrows(EntityNotFoundException.class, () ->
                controller.getEntityModel((long) -1));
    }

    @Test
    void getAll() {
        assertEquals(7, controller.getAll().getContent().size());
    }

    @Test
    void addEntity() {
        Reservation newReservation = controller.getRepository().save(new Reservation(
                time.plusHours(30),
                time.plusHours(33),
                court, client, PlayType.SINGLES_PLAY));
        newReservation = controller.addEntity(newReservation).getContent();
        assertEquals(8, controller.getRepository().count());
        assertThat(newReservation).isNotNull();
        assertEquals(newReservation, controller.getEntity(newReservation.getId()));

        controller.getRepository().delete(newReservation);
    }
}