package tenniscourts.controllers;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.web.bind.annotation.*;
import tenniscourts.entities.*;

import java.math.BigDecimal;
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
     * @return courts in the system
     */
    @GetMapping("/" + CourtController.ROOT_NAME)
    public CollectionModel<EntityModel<Court>> getCourts() {
        return courtController.getAll();
    }

    /**
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
    public BigDecimal newReservation(@RequestBody ReservationPayload payload) {
        Court court = courtController.getEntity(payload.getCourtId());
        List<Client> clients = clientController.getRepository().findAll()
                .stream()
                .filter(client ->
                        client.getPhone_number().equals(payload.getPhoneNumber()))
                .collect(Collectors.toList());

        if (clients.size() > 1) {
            throw new IllegalStateException("phone number" + payload.getPhoneNumber() + "in database is not unique");
        }

        Client client;
        if (clients.isEmpty()) {
            client = clientController.addEntity(payload.getClientName(), payload.getPhoneNumber()).getContent();
        } else {
            client = clients.get(0);
        }

        Reservation reservation = new Reservation(
                payload.getStart(), payload.getEnd(),
                court, client, payload.getPlayType()
        );
        reservationController.addEntity(reservation);
        return reservation.getPrice();
    }
}
