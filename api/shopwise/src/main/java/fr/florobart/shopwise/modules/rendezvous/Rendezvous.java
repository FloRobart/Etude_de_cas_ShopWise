package fr.florobart.shopwise.modules.rendezvous;

import java.sql.Timestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "clients")
public class Rendezvous {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "client_id", nullable = false)
    private Long clientId;
    @Column(name = "commercant_id", nullable = false)
    private Long commercantId;
    @Column(name = "appointment_date", nullable = false)
    private Timestamp appointmentDate;
    @Column(name = "service_type", nullable = true)
    private String serviceType;
    @Column(name = "status", nullable = false)
    private String status;

    public Rendezvous() {}

    public Rendezvous(Long id, Long clientId, Long commercantId, Timestamp appointmentDate, String serviceType, String status) {
        this.id = id;
        this.clientId = clientId;
        this.commercantId = commercantId;
        this.appointmentDate = appointmentDate;
        this.serviceType = serviceType;
        this.status = status;
    }

    /*=========*/
    /* Getters */
    /*=========*/
    public Long getId() {
        return id;
    }

    public Long getClientId() {
        return clientId;
    }

    public Long getCommercantId() {
        return commercantId;
    }

    public Timestamp getAppointmentDate() {
        return appointmentDate;
    }

    public String getServiceType() {
        return serviceType;
    }

    public String getStatus() {
        return status;
    }

    /*=========*/
    /* Setters */
    /*=========*/
    public void setId(Long id) {
        this.id = id;
    }

    public void setClientId(Long clientId) {
        this.clientId = clientId;
    }

    public void setCommercantId(Long commercantId) {
        this.commercantId = commercantId;
    }

    public void setAppointmentDate(Timestamp appointmentDate) {
        this.appointmentDate = appointmentDate;
    }

    public void setServiceType(String serviceType) {
        this.serviceType = serviceType;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
