package tenniscourts.controllers;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;
import tenniscourts.entities.Court;
import tenniscourts.entities.PlayType;
import tenniscourts.entities.Reservation;

/**
 * @author Emma Sommerova
 */

@RestController
public class RequestController {

    private final ClientController clientController;
    private final CourtController courtController;
    private final CourtTypeController courtTypeController;
    private final ReservationController reservationController;

    public RequestController(ClientController clientController,
                             CourtController courtController,
                             CourtTypeController courtTypeController,
                             ReservationController reservationController) {
        this.clientController = clientController;
        this.courtController = courtController;
        this.courtTypeController = courtTypeController;
        this.reservationController = reservationController;
    }

    @GetMapping("/" + CourtController.rootName)
    public CollectionModel<EntityModel<Court>> getCourts() {
        return courtController.getAll();
    }

    @GetMapping("/" + ReservationController.rootName + "/court_id={courtId}")
    public CollectionModel<EntityModel<Reservation>> getReservations(@PathVariable Long courtId) {
        // todo
        return reservationController.getAll();
    }

    @GetMapping("/" + ReservationController.rootName + "/phone_number={phoneNumber}")
    public CollectionModel<EntityModel<Reservation>> getReservations(@PathVariable String phoneNumber) {
        return reservationController.getAll();
        // todo
    }

    @PutMapping("/" + ReservationController.rootName)
    public String newReservation(Long courtId, String clientName, String clientNumber, PlayType playType) {
        // todo
        return null;
    }
}
