package tenniscourts.entities;

import org.junit.jupiter.api.Test;
import tenniscourts.controllers.ReservationController;

import java.math.BigDecimal;
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
        assertEquals(BigDecimal.ZERO, VALID_RESERVATION.getPrice());

        Court court = new Court(new CourtType("new type", BigDecimal.TEN));
        Reservation reservation = new Reservation();
        reservation.cloneAttributes(VALID_RESERVATION);

        reservation.setCourt(court);
        assertEquals(new BigDecimal(600), reservation.getPrice());

        reservation.setEndDateTime(reservation.getEndDateTime().plusMinutes(66));
        assertEquals(new BigDecimal(1260), reservation.getPrice());

        reservation.setEndDateTime(reservation.getStartDateTime().minusHours(1));
        assertThrows(IllegalStateException.class, reservation::getPrice);

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

    @Test
    void checkOverlap() {
        Reservation reservation = VALID_RESERVATION;

        assertTrue(reservation.checkOverlap(
                reservation.getStartDateTime().plusDays(1),
                reservation.getEndDateTime().plusDays(1)));

        assertTrue(reservation.checkOverlap(
                reservation.getStartDateTime().minusDays(1),
                reservation.getEndDateTime().minusDays(1)
        ));

        assertFalse(reservation.checkOverlap(
                reservation.getStartDateTime(),
                reservation.getEndDateTime()
        ));

        assertFalse(reservation.checkOverlap(
                reservation.getStartDateTime().minusMinutes(30),
                reservation.getEndDateTime()
        ));

        assertFalse(reservation.checkOverlap(
                reservation.getStartDateTime(),
                reservation.getEndDateTime().plusMinutes(30)
        ));

        assertFalse(reservation.checkOverlap(
                reservation.getStartDateTime().minusMinutes(30),
                reservation.getEndDateTime().plusMinutes(30)
        ));
    }
}