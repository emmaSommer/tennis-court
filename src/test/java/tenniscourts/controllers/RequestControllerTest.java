package tenniscourts.controllers;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import tenniscourts.entities.*;
import tenniscourts.exceptions.InvalidDeleteException;
import tenniscourts.exceptions.InvalidRequestException;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Emma Sommerova
 */

@SpringBootTest
class RequestControllerTest {

    @Autowired
    RequestController controller;

    @Autowired
    private ClientController clientController;
    @Autowired
    private CourtController courtController;
    @Autowired
    private CourtTypeController courtTypeController;
    @Autowired
    private ReservationController reservationController;


    private Reservation reservation;
    private Client client;
    private Court court;
    private CourtType courtType;

    @BeforeEach
    void setUp() {
        client = clientController.addEntity(ClientTest.VALID_CLIENT);
        courtType = courtTypeController.addEntity(CourtTypeTest.VALID_COURT_TYPE);
        court = courtController.addEntity(courtType);

        ReservationTest.VALID_RESERVATION.setCourt(court);
        ReservationTest.VALID_RESERVATION.setClient(client);
        reservation = reservationController.addEntity(ReservationTest.VALID_RESERVATION);


    }

    @AfterEach
    void tearDown() {
        try {
            reservationController.deleteEntity(reservation.getId());
        } catch (InvalidDeleteException ignored){

        }
        clientController.deleteEntity(client.getId());
        courtController.deleteEntity(court.getId());
        courtTypeController.deleteEntity(courtType.getId());

    }

    @Test
    void getCourts() {
        assertEquals(4, controller.getCourts().getContent().size());
    }

    @Test
    void getReservationsForCourt() {
        CollectionModel<EntityModel<Reservation>> reservations = controller.getReservations(court.getId());
        assertEquals(1, reservations.getContent().size());
    }

    @Test
    void getReservationsForNumber() {
        CollectionModel<EntityModel<Reservation>> reservations = controller.getReservations(client.getPhoneNumber());
        assertEquals(1, reservations.getContent().size());
    }

    @Test
    void newReservation() {
        reservation = reservationController.deleteEntity(reservation.getId());
        ReservationPayload payload = new ReservationPayload(
                reservation.getStartDateTime(),
                reservation.getEndDateTime(),
                court.getId(),
                client.getPhoneNumber(),
                client.getName(),
                PlayType.SINGLES_PLAY);
        BigDecimal price = controller.newReservation(payload);

        assertEquals(0, price.compareTo(BigDecimal.ZERO));
        assertEquals(1, controller.getReservations(court.getId()).getContent().size());
        Reservation r1 = reservationController.getWithCourtId(court.getId()).get(0);
        assertEquals(reservation, r1);
        assertThrows(InvalidDeleteException.class, () -> courtController.deleteEntity(court.getId()));
        assertThrows(InvalidDeleteException.class, () -> clientController.deleteEntity(client.getId()));
        assertThrows(InvalidRequestException.class, () -> controller.newReservation(payload));

        payload.setStart(payload.getStart().minusDays(1));
        payload.setEnd(payload.getEnd().minusDays(1));
        controller.newReservation(payload);
        assertEquals(2, controller.getReservations(court.getId()).getContent().size());

        payload.setStart(payload.getStart().minusDays(1));
        payload.setEnd(payload.getEnd().minusDays(1));
        payload.setClientName("New name");
        assertThrows(InvalidRequestException.class, () -> controller.newReservation(payload));

        payload.setStart(payload.getStart().minusDays(1));
        payload.setEnd(payload.getEnd().minusDays(1));
        payload.setClientName(client.getName());
        payload.setCourtId((long) -1);
        assertThrows(InvalidRequestException.class, () -> controller.newReservation(payload));

        payload.setStart(payload.getStart().minusDays(1));
        payload.setEnd(payload.getEnd().minusDays(1));
        payload.setCourtId(court.getId());
        String newNumber = "420123456789";
        payload.setPhoneNumber(newNumber);
        controller.newReservation(payload);
        Client client  = clientController.getByPhoneNumber(newNumber);

        payload.setStart(payload.getStart().minusDays(1));
        payload.setEnd(payload.getEnd().minusDays(1));
        payload.setCourtId(court.getId());
        payload.setPhoneNumber("420122456789");
        payload.setClientName(" ");
        assertThrows(InvalidRequestException.class, () -> controller.newReservation(payload));

        assertEquals(6, reservationController.getAll().size());


        for (Reservation r: reservationController.getWithCourtId(court.getId())
             ) {
            reservationController.deleteEntity(r.getId());
        }
        clientController.deleteEntity(client.getId());
    }
}