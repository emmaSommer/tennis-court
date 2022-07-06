package tenniscourts.entities;

import tenniscourts.controllers.ReservationController;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import java.sql.Date;

/**
 * Class for representing individual reservation
 * for a specific court bz a specific client
 *
 * @author Emma Sommerova
 */

@Entity
public class Reservation extends SystemEntity {

    public static final String entityName = "reservation";

    @Id
    @GeneratedValue
    private Long id;
    private Date startDate;
    private Date endDate;
    @ManyToOne
    // todo change to static default entities
    private Court court = new Court();
    @ManyToOne
    private Client client = new Client();


    /**
     * Constructor
     *
     * @param start  of the reservation
     * @param end    of the reservation
     * @param court  that is reserved
     * @param client who made the reservation
     */
    public Reservation(Date start, Date end, Court court, Client client) {
        if (start == null || end == null || court == null || client == null) {
            throw new NullArgumentException(entityName,
                    "start: " + start +
                            "end: " + end +
                            "client: " + client +
                            "court: " + court);
        }
        this.startDate = start;
        this.endDate = end;
        this.court = court;
        this.client = client;
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

    public Date getStartDate() {
        return startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public Court getCourt() {
        return court;
    }

    public Client getClient() {
        return client;
    }

    @Override
    public String getRootName() {
        return ReservationController.rootName;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public void setCourt(Court court) {
        this.court = court;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    @Override
    public String toString() {
        return "Reservation{" +
                "id=" + id +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", court=" + court +
                ", client=" + client +
                '}';
    }
}
