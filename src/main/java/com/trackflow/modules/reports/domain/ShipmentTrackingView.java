package com.trackflow.modules.reports.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.Instant;

/**
 * Modelo de lectura de reports: solo los datos que el cliente puede ver.
 * Se alimenta de los eventos de shipments y logistics.
 */
@Entity
@Table(name = "reports_shipment_tracking")
public class ShipmentTrackingView {

    @Id
    private String trackingNumber;

    @Column(nullable = false)
    private String status;

    @Column(nullable = false)
    private String senderName;

    @Column(nullable = false)
    private Long originCityId;

    @Column(nullable = false)
    private String originCity;

    @Column(nullable = false)
    private String recipientName;

    /**
     * Se guarda siempre, pero solo se expone en la respuesta cuando el envío queda
     * ENTREGADO: antes de eso el paquete no ha llegado ahí todavía.
     */
    @Column(nullable = false)
    private String recipientAddress;

    @Column(nullable = false)
    private Long destinationCityId;

    @Column(nullable = false)
    private String destinationCity;

    @Column(nullable = false)
    private Instant registeredAt;

    @Column
    private String lastMovementPoint;

    @Column
    private Instant lastMovementAt;

    protected ShipmentTrackingView() {
    }

    public ShipmentTrackingView(String trackingNumber, String status, String senderName, Long originCityId,
                                String originCity, String recipientName, String recipientAddress,
                                Long destinationCityId, String destinationCity, Instant registeredAt) {
        this.trackingNumber = trackingNumber;
        this.status = status;
        this.senderName = senderName;
        this.originCityId = originCityId;
        this.originCity = originCity;
        this.recipientName = recipientName;
        this.recipientAddress = recipientAddress;
        this.destinationCityId = destinationCityId;
        this.destinationCity = destinationCity;
        this.registeredAt = registeredAt;
    }

    /**
     * Ignora los movimientos anteriores al último aplicado: los reportes no llegan
     * necesariamente en orden (un lector sin señal sincroniza tarde, o el broker
     * reentrega un mensaje), y sin esta comprobación un reporte rezagado devolvía el
     * estado a uno anterior aunque el envío ya hubiera avanzado.
     *
     * @return false si el movimiento es anterior al último aplicado y se ignora
     */
    public boolean registrarMovimiento(String newStatus, String point, Instant movedAt) {
        if (lastMovementAt != null && movedAt.isBefore(lastMovementAt)) {
            return false;
        }

        this.status = newStatus;
        this.lastMovementPoint = point;
        this.lastMovementAt = movedAt;

        return true;
    }

    public String getTrackingNumber() { return trackingNumber; }
    public String getStatus() { return status; }
    public String getSenderName() { return senderName; }
    public Long getOriginCityId() { return originCityId; }
    public String getOriginCity() { return originCity; }
    public String getRecipientName() { return recipientName; }
    public String getRecipientAddress() { return recipientAddress; }
    public Long getDestinationCityId() { return destinationCityId; }
    public String getDestinationCity() { return destinationCity; }
    public Instant getRegisteredAt() { return registeredAt; }
    public String getLastMovementPoint() { return lastMovementPoint; }
    public Instant getLastMovementAt() { return lastMovementAt; }
}
