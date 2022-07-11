package tenniscourts.entities;

import com.sun.istack.NotNull;
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
 * for a specific court made by a specific client
 *
 * @author Emma Sommerova
 */

@Entity
public class Reservation extends SystemEntity {

    public static final String ENTITY_NAME = "reservation";
    public static final int MAX_INTERVAL = 600;
    @Id
    @GeneratedValue
    private Long id;
    @NotNull
    private LocalDateTime startDateTime;
    @NotNull
    private LocalDateTime endDateTime;
    @ManyToOne
    @NotNull
    private Court court;
    @ManyToOne
    @NotNull
    private Client client;
    private PlayType playType = PlayType.SINGLES_PLAY;


    /**
     * @param start of the interval
     * @param end   of the interval
     * @return true if the star and end parameters create an interval
     * suitable for reservation
     */
    public static boolean validInterval(LocalDateTime start, LocalDateTime end) {
        return start != null && end != null && end.isAfter(start)
                && Duration.between(start, end).toMinutes() < MAX_INTERVAL;
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

    /**
     * @param start of new interval
     * @param end   of new interval
     * @return true if the given interval does not overlap with the reservation interval
     */
    public boolean checkOverlap(LocalDateTime start, LocalDateTime end) {
        return start.isBefore(this.startDateTime) && end.isBefore(this.startDateTime)
                || start.isAfter(this.startDateTime) && end.isAfter(this.endDateTime);
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

    /**
     * @return price of the reservation for the time interval
     * @throws IllegalStateException if the reservation is not in a valid state
     */
    public BigDecimal getPrice() {
        if (!isValid()) {
            throw new IllegalStateException("Can not get price for invalid reservation");
        }

        BigDecimal minutes = BigDecimal.valueOf(
                Duration.between(startDateTime, endDateTime).toMinutes());

        BigDecimal price = court.getCourtType().getPrice();
        if (playType == PlayType.DOUBLES_PLAY) {
            return price.multiply(minutes).multiply(BigDecimal.valueOf(1.5));
        }
        return price.multiply(minutes);
    }

    public void setStartDateTime(LocalDateTime startDateTime) {
        if (startDateTime == null) {
            throw new IllegalArgumentException();
        }
        this.startDateTime = startDateTime;
    }

    public void setEndDateTime(LocalDateTime endDateTime) {
        if (endDateTime == null) {
            throw new IllegalArgumentException();
        }
        this.endDateTime = endDateTime;
    }

    public void setCourt(Court court) {
        if (court == null) {
            throw new IllegalArgumentException();
        }
        this.court = court;
    }

    public void setClient(Client client) {
        if (client == null) {
            throw new IllegalArgumentException();
        }
        this.client = client;
    }

    public void setPlayType(PlayType playType) {
        if (playType == null) {
            throw new IllegalArgumentException();
        }
        this.playType = playType;
    }

    @Override
    public void cloneAttributes(SystemEntity newEntity) {
        if (newEntity.getClass() != Reservation.class) {
            throw new IllegalArgumentException("Can't clone " + newEntity.getClass() + " into Reservation");
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
        if (!super.equals(o)){
            return false;
        }
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
