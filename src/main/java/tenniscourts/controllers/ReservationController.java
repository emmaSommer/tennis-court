package tenniscourts.controllers;

import org.springframework.web.bind.annotation.RestController;
import tenniscourts.entities.Client;
import tenniscourts.entities.Court;
import tenniscourts.entities.PlayType;
import tenniscourts.entities.Reservation;
import tenniscourts.exceptions.EntityNotFoundException;
import tenniscourts.exceptions.InvalidEntityException;
import tenniscourts.exceptions.InvalidIdException;
import tenniscourts.storage.ReservationRepository;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Entity controller for the Reservation class
 *
 * @author Emma Sommerova
 */

@RestController
public class ReservationController extends EntityController<Reservation> {

    public static final String ROOT_NAME = "reservations";

    private final ReservationRepository repository;

    /**
     * Constructor
     *
     * @param repository for the Reservation entity
     */
    public ReservationController(ReservationRepository repository) {
        this.repository = repository;
    }

    @Override
    public ReservationRepository getRepository() {
        return repository;
    }

    @Override
    public String getEntityName() {
        return Reservation.ENTITY_NAME;
    }

    @Override
    public String getRootName() {
        return ROOT_NAME;
    }


    /**
     * @param clientId id of the client
     * @return list of reservation for the given client
     * @throws EntityNotFoundException when client has no reservations
     */
    public List<Reservation> getWithClientId(Long clientId) {

        List<Reservation> reservations = repository.findAllByClient_Id(clientId);
        if (reservations.isEmpty()) {
            throw new EntityNotFoundException("reservations for client: " + clientId);
        }
        return reservations;
    }

    /**
     * @param courtId id of the Court
     * @return list of reservations for the given court
     * @throws EntityNotFoundException when the court has no reservations
     */
    public List<Reservation> getWithCourtId(Long courtId) {
        List<Reservation> reservations = repository.findAllByCourt_Id(courtId);
        if (reservations.isEmpty()) {
            throw new EntityNotFoundException("reservations for client: " + courtId);
        }
        return reservations;
    }

    /**
     * @param start    of reservation
     * @param end      of reservation
     * @param court    the reservation is for
     * @param playType what play is the reservation for
     * @param client   making the reservation
     * @return new Reservation instance
     * @throws InvalidIdException the the court is already booked for the given interval
     */
    public Reservation addEntity(LocalDateTime start, LocalDateTime end,
                                 Court court, PlayType playType,
                                 Client client) {

        if (!isAvailable(start, end, court)) {
            throw new InvalidEntityException("Court is already booked for this time interval");
        }
        Reservation reservation = new Reservation(start, end, court, client, playType);
        return super.addEntity(reservation);
    }

    /**
     * @param start of new reservation
     * @param end   of new reservation
     * @param court of new reservation
     * @return true when there are no conflicting reservations
     */
    public boolean isAvailable(LocalDateTime start, LocalDateTime end, Court court) {
        List<Reservation> reservations = repository.findAllByCourt_Id(court.getId());
        for (Reservation reservation : reservations
        ) {
            if (!reservation.checkOverlap(start, end)) {
                return false;
            }
        }
        return true;
    }
}
