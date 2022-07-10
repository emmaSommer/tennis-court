package tenniscourts.entities;

import tenniscourts.controllers.ReservationController;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * Class for representing individual reservation
 * for a specific court bz a specific client
 *
 * @author Emma Sommerova
 */

@Entity
public class Reservation extends SystemEntity {

    public static final String ENTITY_NAME = "reservation";
    public static final int MAX_INTERVAL = 10;
    @Id
    @GeneratedValue
    private Long id;
    private LocalDateTime startDateTime;
    private LocalDateTime endDateTime;
    @ManyToOne
    private Court court;
    @ManyToOne
    private Client client;
    private PlayType playType = PlayType.SINGLES_PLAY;


    public static boolean validInterval(LocalDateTime start, LocalDateTime end) {
        return start != null && end != null && end.isAfter(start);
        // todo - debug this condition
        //&& Duration.between(start, end).toMinutes() < MAX_INTERVAL;
    }


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
    public boolean isValid() {
        return validInterval(startDateTime, endDateTime)
                && court != null && court.isValid()
                && client != null && client.isValid()
                && playType != null;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Reservation that = (Reservation) o;
        return Objects.equals(id, that.id)
                && Objects.equals(startDateTime, that.startDateTime)
                && Objects.equals(endDateTime, that.endDateTime)
                && Objects.equals(court, that.court)
                && Objects.equals(client, that.client)
                && playType == that.playType;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, startDateTime, endDateTime, court, client, playType);
    }
}
