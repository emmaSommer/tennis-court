package tenniscourts.controllers;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import tenniscourts.entities.Court;
import tenniscourts.entities.CourtTest;
import tenniscourts.entities.CourtType;
import tenniscourts.storage.CourtRepository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Emma Sommerova
 */

@SpringBootTest
class CourtControllerTest {

    @Autowired
    private ClientController clientController;
    @Autowired
    private CourtController controller;
    @Autowired
    private CourtTypeController courtTypeController;
    @Autowired
    private ReservationController reservationController;

    private Court court;

    @BeforeEach
    void setUp() {
        court = controller.getRepository().save(new Court(courtTypeController.getRepository().findAll().get(0)));

    }

    @AfterEach
    void tearDown(){
        controller.getRepository().delete(court);
    }

    @Test
    void getEntityName() {
        assertEquals(Court.ENTITY_NAME, controller.getEntityName());
    }

    @Test
    void getEntityModel() {
        assertEquals(court, controller.getEntityModel(court.getId()).getContent());

        assertThrows(EntityNotFoundException.class, () ->
                controller.getEntity((long) -1));
    }

    @Test
    void addEntity() {
        CourtType type = courtTypeController.getRepository().findAll().get(0);
        Court newCourt = controller.addEntity(type).getContent();
        assertEquals(5, controller.getRepository().count());
        assertThat(newCourt).isNotNull();
        assertEquals(newCourt, controller.getEntity(newCourt.getId()));

        controller.getRepository().delete(newCourt);

    }
}