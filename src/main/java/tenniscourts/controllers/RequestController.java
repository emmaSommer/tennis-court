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
        CollectionModel<EntityModel<Reservation>> model = SystemEntity.toCollectionModel(
                reservationController.getRepository().findAllByCourt_Id(courtId), reservationController
        );

        return model.add(
                linkTo(methodOn(RequestController.class).getReservations(courtId)).withSelfRel(),
                linkTo(methodOn(reservationController.getClass()).getAll()).withRel(ReservationController.ROOT_NAME)
        );
    }

    @GetMapping("/" + ReservationController.ROOT_NAME + "/phone_number={phoneNumber}")
    // todo - finish this method
    public CollectionModel<EntityModel<Reservation>> getReservations(@PathVariable String phoneNumber) {
        Client client = clientController.getByPhoneNumber(phoneNumber);
        CollectionModel<EntityModel<Reservation>> model = SystemEntity.toCollectionModel(
                reservationController.getRepository().findAllByClient_Id(client.getId()), reservationController);
        return model.add(
                linkTo(methodOn(RequestController.class).getReservations(phoneNumber)).withSelfRel(),
                linkTo(methodOn(ReservationController.class).getAll()).withRel(ReservationController.ROOT_NAME));
    }

    @PutMapping("/" + ReservationController.ROOT_NAME)
    public BigDecimal newReservation(@RequestBody ReservationPayload payload) {
        Court court = courtController.getEntity(payload.getCourtId());
        List<Client> clients = clientController.getRepository().findAll()
                .stream()
                .filter(client ->
                        client.getPhoneNumber().equals(payload.getPhoneNumber()))
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
