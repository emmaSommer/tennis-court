package tenniscourts.entities;

import tenniscourts.controllers.ReservationController;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import java.sql.Date;

/**
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

    public Reservation(Date start, Date end, Court court, Client client) {
        this.startDate = start;
        this.endDate = end;
        this.court = court;
        this.client = client;
    }

    public Reservation(){}

    @Override
    public String getRootName(){
        return ReservationController.rootName;
    }

    @Override
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Court getCourt() {
        return court;
    }

    public void setCourt(Court court) {
        this.court = court;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }
}
