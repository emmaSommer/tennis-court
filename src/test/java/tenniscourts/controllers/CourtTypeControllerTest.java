package tenniscourts.controllers;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import tenniscourts.entities.CourtType;
import tenniscourts.entities.CourtTypeTest;
import tenniscourts.exceptions.EntityNotFoundException;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;


/**
 * @author Emma Sommerova
 */

@SpringBootTest
class CourtTypeControllerTest {

    @Autowired
    private ClientController clientController;
    @Autowired
    private CourtController courtController;
    @Autowired
    private CourtTypeController controller;
    @Autowired
    private ReservationController reservationController;
    CourtType type = CourtTypeTest.VALID_COURT_TYPE;

    @BeforeEach
    public void setUp(){
        type = controller.getRepository().save(type);

    }

    @AfterEach
    public void tearDown(){
        controller.getRepository().delete(type);
    }

    @Test
    void getAll() {
        assertEquals(4, controller.getCollectionModel().getContent().size());
    }

    @Test
    void getEntityName() {
        assertEquals(controller.getEntityName(), CourtType.ENTITY_NAME);
    }

    @Test
    void getEntityModel() {
        assertEquals(type, controller.getEntityModel(type.getId()).getContent());

        assertThrows(EntityNotFoundException.class, () ->
                controller.getEntityModel((long) -1));
    }

    @Test
    void addEntity() {
        CourtType newType = controller.addEntity("new type", BigDecimal.valueOf(0.00));
        assertEquals(5, controller.getRepository().count() );
        assertThat(newType).isNotNull();
        assertEquals(newType, controller.getEntity(newType.getId()));

        controller.getRepository().delete(newType);
    }
}