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
    private Client client = new Client("Frodo", "420903030030");


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
        assertTrue(client.isValid());
        client.setName(null);
        assertFalse(client.isValid());
        client.setName("  ");
        assertFalse(client.isValid());
        client.setName("Frodo");
        client.setPhoneNumber("");
        assertFalse(client.isValid());
        client.setPhoneNumber("420903030030");
    }

    @Test
    void getRootName() {
        assertThat(client.getRootName()).isEqualTo(ClientController.ROOT_NAME);
    }

    @Test
    void cloneAttributes() {
        Client duplicate = new Client("Henry", "123456789987");
        duplicate.cloneAttributes(client);
        assertEquals(duplicate.getName(), client.getName());
        assertEquals(duplicate.getPhoneNumber(), client.getPhoneNumber());

        assertThrows(IllegalArgumentException.class, () ->
                duplicate.cloneAttributes(new CourtType("default", BigDecimal.ONE)));
    }

}