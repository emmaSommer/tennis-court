package tenniscourts.entities;

import org.junit.jupiter.api.Test;

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

        reservation.setStartDateTime(VALID_RESERVATION.getEndDateTime());
        assertFalse(reservation.isValid());

        assertFalse((new Reservation(null, null, null, null, null)).isValid());
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

        assertTrue(reservation.withoutOverlap(
                reservation.getStartDateTime().plusDays(1),
                reservation.getEndDateTime().plusDays(1)));

        assertTrue(reservation.withoutOverlap(
                reservation.getStartDateTime().minusDays(1),
                reservation.getEndDateTime().minusDays(1)
        ));

        assertFalse(reservation.withoutOverlap(
                reservation.getStartDateTime(),
                reservation.getEndDateTime()
        ));

        assertFalse(reservation.withoutOverlap(
                reservation.getStartDateTime().minusMinutes(30),
                reservation.getEndDateTime()
        ));

        assertFalse(reservation.withoutOverlap(
                reservation.getStartDateTime(),
                reservation.getEndDateTime().plusMinutes(30)
        ));

        assertFalse(reservation.withoutOverlap(
                reservation.getStartDateTime().minusMinutes(30),
                reservation.getEndDateTime().plusMinutes(30)
        ));
    }
}