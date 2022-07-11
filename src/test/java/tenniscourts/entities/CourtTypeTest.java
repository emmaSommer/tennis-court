package tenniscourts.entities;

import org.junit.jupiter.api.Test;
import tenniscourts.controllers.CourtTypeController;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Emma Sommerova
 */
public class CourtTypeTest {

    public static final CourtType VALID_COURT_TYPE = new CourtType("valid", BigDecimal.ZERO);

    @Test
    void isValid() {
        assertTrue(VALID_COURT_TYPE.isValid());
        CourtType type = new CourtType(null, null);
        assertFalse(type.isValid());
        type.setPrice(BigDecimal.ZERO);
        type.setName("");
        assertFalse(type.isValid());
        type.setName("    ");
        assertFalse(type.isValid());

    }

    @Test
    void getRootName() {
        assertEquals(VALID_COURT_TYPE.getRootName(), CourtTypeController.ROOT_NAME);
    }

    @Test
    void cloneAttributes() {
        CourtType duplicate = new CourtType();
        duplicate.cloneAttributes(VALID_COURT_TYPE);
        assertEquals(duplicate.getName(), VALID_COURT_TYPE.getName());
        assertEquals(duplicate.getPrice(), VALID_COURT_TYPE.getPrice());

        assertThrows(IllegalArgumentException.class, () ->
                duplicate.cloneAttributes(CourtTest.VALID_COURT)
                );
    }
}