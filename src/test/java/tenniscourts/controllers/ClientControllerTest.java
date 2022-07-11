package tenniscourts.controllers;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import tenniscourts.entities.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Emma Sommerova
 */

@SpringBootTest
class ClientControllerTest {

    @Autowired
    private ClientController controller;
    @Autowired
    private CourtController courtController;
    @Autowired
    private CourtTypeController courtTypeController;
    @Autowired
    private ReservationController ReservationController;

    private Reservation reservation;
    private Client client = ClientTest.VALID_CLIENT;
    private Court court;
    private CourtType courtType;

    @BeforeEach
    void setUp() {
        client = controller.getRepository().save(client);

    }

    @AfterEach
    void tearDown() {
        controller.getRepository().delete(client);
    }

    @Test
    void getEntityName() {
        assertEquals(controller.getEntityName(), Client.ENTITY_NAME);
    }

    @Test
    void getEntityModel() {
        assertEquals(client, controller.getEntityModel(client.getId()).getContent());

        assertThrows(EntityNotFoundException.class, () ->
                controller.getEntityModel((long) -1));
    }

    @Test
    void getAll() {
        assertEquals(3, controller.getCollectionModel().getContent().size());
    }

    @Test
    void getByPhoneNumber() {
        assertEquals(client, controller.getByPhoneNumber(client.getPhoneNumber()));

    }

    @Test
    void addEntity() {

        Client newClient = controller.addEntity("Harry Potter", "123456789987");
        assertEquals(4, controller.getRepository().count());
        assertThat(newClient).isNotNull();
        assertEquals(newClient, controller.getEntity(newClient.getId()));
        controller.getRepository().delete(newClient);
    }
}