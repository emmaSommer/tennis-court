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
import tenniscourts.entities.SystemEntity;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

/**
 * REST endpoints for application
 *
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

    /**
     *
     * @return courts in the system
     */
    @GetMapping("/" + CourtController.ROOT_NAME)
    public CollectionModel<EntityModel<Court>> getCourts() {
        return courtController.getAll();
    }

    /**
     *
     * @param courtId id
     * @return reservations on the court with the given id
     */
    @GetMapping("/" + ReservationController.ROOT_NAME + "/court_id={courtId}")
    public CollectionModel<EntityModel<Reservation>> getReservations(@PathVariable Long courtId) {
        List<EntityModel<Reservation>> reservations = reservationController.getRepository().findAll()
                .stream()
                .filter(reservation -> reservation.getCourt().getId().equals(courtId))
                .map(reservation -> SystemEntity.toModel(reservation, reservationController))
                .collect(Collectors.toList());
        return CollectionModel.of(
                reservations,
                linkTo(methodOn(RequestController.class).getReservations(courtId)).withSelfRel(),
                linkTo(methodOn(reservationController.getClass()).getAll()).withRel(ReservationController.ROOT_NAME)
        );
    }

    @GetMapping("/" + ReservationController.ROOT_NAME + "/phone_number={phoneNumber}")
    public CollectionModel<EntityModel<Reservation>> getReservations(@PathVariable String phoneNumber) {
        List<EntityModel<Reservation>> reservations = reservationController.getRepository().findAll()
                .stream()
                .filter(reservation -> reservation.getClient().getPhone_number().equals(phoneNumber))
                .map(reservation -> SystemEntity.toModel(reservation, reservationController))
                .collect(Collectors.toList());
        return CollectionModel.of(
                reservations,
                linkTo(methodOn(RequestController.class).getReservations(phoneNumber)).withSelfRel(),
                linkTo(methodOn(reservationController.getClass()).getAll()).withRel(ReservationController.ROOT_NAME)
        );
    }

    @PutMapping("/" + ReservationController.ROOT_NAME)
    public String newReservation(Long courtId, String clientName, String clientNumber, PlayType playType) {
        // todo
        return null;
    }
}
