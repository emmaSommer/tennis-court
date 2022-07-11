package tenniscourts.entities;

import org.junit.jupiter.api.Test;
import tenniscourts.controllers.CourtController;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Emma Sommerova
 */
public class CourtTest {

    public final static Court VALID_COURT = new Court(CourtTypeTest.VALID_COURT_TYPE);
    Court court;

    public CourtTest(){
        CourtType type = new CourtType("default", BigDecimal.ONE);
        this.court = new Court(type);
    }

    @Test
    void isValid() {
        CourtType type = new CourtType("default", BigDecimal.ONE);
        Court court = new Court(type);
        assertTrue(court.isValid());
        type.setName("  ");
        assertFalse(court.isValid());
    }

    @Test
    void getRootName() {
        assertEquals(court.getRootName(), CourtController.ROOT_NAME);
    }

    @Test
    void cloneAttributes() {
        CourtType duplicateType = new CourtType("type", BigDecimal.ZERO);
        Court duplicate = new Court(duplicateType);

        duplicate.cloneAttributes(court);
        assertEquals(duplicate.getCourtType(), court.getCourtType());

        assertThrows(IllegalArgumentException.class, () ->
                duplicate.cloneAttributes(ClientTest.VALID_CLIENT));


    }
}