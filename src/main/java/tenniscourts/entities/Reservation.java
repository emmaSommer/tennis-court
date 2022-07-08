package tenniscourts.entities;

import tenniscourts.controllers.ReservationController;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDateTime;

/**
 * Class for representing individual reservation
 * for a specific court bz a specific client
 *
 * @author Emma Sommerova
 */

@Entity
public class Reservation extends SystemEntity {

    public static final String ENTITY_NAME = "reservation";

    @Id
    @GeneratedValue
    private Long id;
    private LocalDateTime startDateTime;
    private LocalDateTime endDateTime;
    @ManyToOne
    // todo change to static default entities
    private Court court = new Court();
    @ManyToOne
    private Client client = new Client();
    private PlayType playType = PlayType.SINGLES_PLAY;


    /**
     * Constructor
     *
     * @param start  of the reservation
     * @param end    of the reservation
     * @param court  that is reserved
     * @param client who made the reservation
     */
    public Reservation(LocalDateTime start, LocalDateTime end,
                       Court court, Client client, PlayType playType) {
        if (start == null || end == null ||
                court == null || client == null || playType == null) {
            throw new NullArgumentException(ENTITY_NAME,
                    "start: " + start +
                            "end: " + end +
                            "client: " + client +
                            "court: " + court);
        }
        this.startDateTime = start;
        this.endDateTime = end;
        this.court = court;
        this.client = client;
        this.playType = playType;
    }

    /**
     * Default constructor
     */
    public Reservation() {
    }


    @Override
    public Long getId() {
        return id;
    }

    public LocalDateTime getStartDateTime() {
        return startDateTime;
    }

    public LocalDateTime getEndDateTime() {
        return endDateTime;
    }

    public Court getCourt() {
        return court;
    }

    public Client getClient() {
        return client;
    }

    public PlayType getPlayType() {
        return playType;
    }

    @Override
    public String getRootName() {
        return ReservationController.ROOT_NAME;
    }

    public BigDecimal getPrice() {
        BigDecimal minutes = BigDecimal.valueOf(
                Duration.between(startDateTime, endDateTime).toMinutes());

        BigDecimal price = court.getType().getPrice();
        if (playType == PlayType.DOUBLES_PLAY) {
            return price.multiply(minutes).multiply(BigDecimal.valueOf(1.5));
        }
        return price.multiply(minutes);
    }

    public void setStartDateTime(LocalDateTime startDateTime) {
        this.startDateTime = startDateTime;
    }

    public void setEndDateTime(LocalDateTime endDateTime) {
        this.endDateTime = endDateTime;
    }

    public void setCourt(Court court) {
        this.court = court;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public void setPlayType(PlayType playType) {
        this.playType = playType;
    }

    @Override
    public void cloneAttributes(SystemEntity newEntity) {
        if (newEntity.getClass() != Reservation.class) {
            throw new IllegalArgumentException();
        }
        Reservation newReservation = (Reservation) newEntity;
        this.startDateTime = newReservation.getStartDateTime();
        this.endDateTime = newReservation.getEndDateTime();
        this.court = newReservation.getCourt();
        this.client = newReservation.getClient();
        this.playType = newReservation.getPlayType();
    }

    @Override
    public String toString() {
        return "Reservation{" +
                "id=" + id +
                ", startDateTime=" + startDateTime +
                ", endDateTime=" + endDateTime +
                ", court=" + court +
                ", client=" + client +
                ", playType=" + playType +
                '}';
    }
}
