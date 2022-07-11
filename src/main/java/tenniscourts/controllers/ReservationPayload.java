package tenniscourts.controllers;

import tenniscourts.entities.PlayType;

import java.time.LocalDateTime;

/**
 * @author Emma Sommerova
 */
public class ReservationPayload {

    private LocalDateTime start;
    private LocalDateTime end;
    private Long courtId;
    private String phoneNumber;
    private String clientName;
    private PlayType playType;

    public ReservationPayload(LocalDateTime start,
                              LocalDateTime end,
                              Long courtId,
                              String phoneNumber,
                              String clientName,
                              PlayType playType) {
        this.start = start;
        this.end = end;
        this.courtId = courtId;
        this.phoneNumber = phoneNumber;
        this.clientName = clientName;
        this.playType = playType;
    }

    public ReservationPayload() {
    }

    public LocalDateTime getStart() {
        return start;
    }

    public void setStart(LocalDateTime start) {
        this.start = start;
    }

    public LocalDateTime getEnd() {
        return end;
    }

    public void setEnd(LocalDateTime end) {
        this.end = end;
    }

    public Long getCourtId() {
        return courtId;
    }

    public void setCourtId(Long courtId) {
        this.courtId = courtId;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public PlayType getPlayType() {
        return playType;
    }

    public void setPlayType(PlayType playType) {
        this.playType = playType;
    }
}
