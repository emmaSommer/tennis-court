package tenniscourts.entities;

import org.junit.jupiter.api.Test;
import tenniscourts.controllers.ClientController;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Emma Sommerova
 */
public class ClientTest {

    public static final Client VALID_CLIENT = new Client("Frodo", "123456789012");

    @Test
    void validNumber() {
        assertTrue(Client.validNumber("420903030030"));
        assertFalse(Client.validNumber("+420903030030"));
        assertFalse(Client.validNumber("+420903030030"));
        assertFalse(Client.validNumber("420a03030030"));
        assertFalse(Client.validNumber("420"));
        assertFalse(Client.validNumber("            "));
        assertFalse(Client.validNumber(null));
    }

    @Test
    void isValid() {
        assertTrue(VALID_CLIENT.isValid());

        Client client = new Client();
        client.cloneAttributes(VALID_CLIENT);

        client.setName("  ");
        assertFalse(client.isValid());

        client.setName("Frodo");
        client.setPhoneNumber("");
        assertFalse(client.isValid());
    }

    @Test
    void getRootName() {
        assertEquals(ClientController.ROOT_NAME, VALID_CLIENT.getRootName());
    }

    @Test
    void cloneAttributes() {
        Client duplicate = new Client("Henry", "123456789987");
        duplicate.cloneAttributes(VALID_CLIENT);
        assertEquals(duplicate.getName(), VALID_CLIENT.getName());
        assertEquals(duplicate.getPhoneNumber(), VALID_CLIENT.getPhoneNumber());

        assertThrows(IllegalArgumentException.class, () ->
                duplicate.cloneAttributes(new CourtType("default", BigDecimal.ONE)));
    }

}