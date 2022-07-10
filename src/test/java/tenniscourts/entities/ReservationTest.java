package tenniscourts.entities;

import org.junit.jupiter.api.Test;
import tenniscourts.controllers.ReservationController;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Emma Sommerova
 */
public class ReservationTest {

    public static final Reservation VALID_RESERVATION = new Reservation(
            LocalDateTime.now(), LocalDateTime.now().plusHours(1),
            CourtTest.VALID_COURT, ClientTest.VALID_CLIENT, PlayType.SINGLES_PLAY
    );

    @Test
    void validInterval() {
        assertTrue(Reservation.validInterval(
                VALID_RESERVATION.getStartDateTime(),
                VALID_RESERVATION.getEndDateTime()));
        assertFalse(Reservation.validInterval(
                VALID_RESERVATION.getEndDateTime(),
                VALID_RESERVATION.getStartDateTime()
        ));
        assertFalse(Reservation.validInterval(
                VALID_RESERVATION.getStartDateTime(),
                VALID_RESERVATION.getStartDateTime()
        ));
        assertFalse(Reservation.validInterval(
                VALID_RESERVATION.getStartDateTime(),
                VALID_RESERVATION.getStartDateTime().plusHours(12)
        ));
        assertFalse(Reservation.validInterval(null, null));
    }

    @Test
    void isValid() {
        Reservation reservation = new Reservation();
        reservation.cloneAttributes(VALID_RESERVATION);
        assertTrue(reservation.isValid());

        reservation.setStartDateTime(null);
        assertFalse(reservation.isValid());
        reservation.setStartDateTime(VALID_RESERVATION.getStartDateTime());

        reservation.setEndDateTime(null);
        assertFalse(reservation.isValid());
        reservation.setEndDateTime(VALID_RESERVATION.getEndDateTime());

        reservation.setCourt(null);
        assertFalse(reservation.isValid());
        reservation.setCourt(VALID_RESERVATION.getCourt());

        reservation.setClient(null);
        assertFalse(reservation.isValid());
        reservation.setClient(VALID_RESERVATION.getClient());

        reservation.setPlayType(null);
        assertFalse(reservation.isValid());
        reservation.setPlayType(VALID_RESERVATION.getPlayType());

        reservation.setStartDateTime(VALID_RESERVATION.getEndDateTime());
        assertFalse(reservation.isValid());
    }

    @Test
    void getRootName() {
        assertEquals(VALID_RESERVATION.getRootName(), ReservationController.ROOT_NAME);
    }

    @Test
    void getPrice() {
        // todo
    }

    @Test
    void cloneAttributes() {
        Reservation duplicate = new Reservation();
        duplicate.cloneAttributes(VALID_RESERVATION);

        assertEquals(duplicate.getStartDateTime(), VALID_RESERVATION.getStartDateTime());
        assertEquals(duplicate.getEndDateTime(), VALID_RESERVATION.getEndDateTime());
        assertEquals(duplicate.getCourt(), VALID_RESERVATION.getCourt());
        assertEquals(duplicate.getClient(), VALID_RESERVATION.getClient());
        assertEquals(duplicate.getPlayType(), VALID_RESERVATION.getPlayType());

        assertThrows(IllegalArgumentException.class, () ->
                duplicate.cloneAttributes(CourtTest.VALID_COURT));
    }
}