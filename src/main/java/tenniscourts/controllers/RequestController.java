package tenniscourts.controllers;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.web.bind.annotation.*;
import tenniscourts.entities.*;
import tenniscourts.exceptions.EntityNotFoundException;
import tenniscourts.exceptions.InvalidEntityException;
import tenniscourts.exceptions.InvalidRequestException;

import java.math.BigDecimal;

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
    private final ReservationController reservationController;


    public RequestController(ClientController clientController,
                             CourtController courtController,
                             ReservationController reservationController) {
        this.clientController = clientController;
        this.courtController = courtController;
        this.reservationController = reservationController;
    }

    /**
     * @return courts in the system
     * @throws EntityNotFoundException if there are no courts
     */
    @GetMapping("/" + CourtController.ROOT_NAME)
    public CollectionModel<EntityModel<Court>> getCourts() {
        return courtController.getCollectionModel();
    }

    /**
     * @param courtId id
     * @return reservations on the court with the given id
     * @throws EntityNotFoundException if there are no reservations
     */
    @GetMapping("/" + ReservationController.ROOT_NAME + "/court_id={courtId}")
    public CollectionModel<EntityModel<Reservation>> getReservations(@PathVariable Long courtId) {
        CollectionModel<EntityModel<Reservation>> model = SystemEntity.toCollectionModel(
                reservationController.getWithCourtId(courtId), reservationController);

        return model.add(
                linkTo(methodOn(RequestController.class).getReservations(courtId)).withSelfRel()
        );
    }

    /**
     * @param phoneNumber of a client
     * @return list of reservation associated with the number
     * @throws EntityNotFoundException if there are no reservation for the number
     */
    @GetMapping("/" + ReservationController.ROOT_NAME + "/phone_number={phoneNumber}")
    public CollectionModel<EntityModel<Reservation>> getReservations(@PathVariable String phoneNumber) {
        Client client = clientController.getByPhoneNumber(phoneNumber);
        CollectionModel<EntityModel<Reservation>> model = SystemEntity.toCollectionModel(
                reservationController.getRepository().findAllByClient_Id(client.getId()), reservationController);
        return model.add(
                linkTo(methodOn(RequestController.class).getReservations(phoneNumber)).withSelfRel());
    }

    /**
     * Creates new reservation and saves it into the repository
     *
     * @param payload information about new reservation
     * @return price of the new reservation
     * @throws IllegalArgumentException when reservation is invalid
     */
    @PutMapping("/" + ReservationController.ROOT_NAME)
    public BigDecimal newReservation(@RequestBody ReservationPayload payload) {
        try {
            Client client = processClient(payload.getClientName(), payload.getPhoneNumber());
            Court court = courtController.getEntity(payload.getCourtId());
            Reservation reservation = new Reservation(
                    payload.getStart(), payload.getEnd(),
                    court, client, payload.getPlayType()
            );

            reservation = reservationController.addEntity(reservation);
            return reservation.getPrice();

        } catch (EntityNotFoundException | InvalidEntityException e) {
            throw new InvalidRequestException("Can't create reservation\n\t" + e.getMessage());
        }
    }

    /**
     * Retrieves client from repository or creates a new record
     *
     * @param name   of the client
     * @param number of the client
     * @return Client instance with the given name and number saved in the repository
     * @throws InvalidEntityException when the number exists under different name
     */
    private Client processClient(String name, String number) {
        try {
            Client client = clientController.getByPhoneNumber(number);
            if (!client.getName().equals(name)) {
                throw new InvalidEntityException("Phone number is associated with different name");
            }
            return client;

        } catch (EntityNotFoundException e) {
            return clientController.addEntity(name, number);

        }
    }
}
