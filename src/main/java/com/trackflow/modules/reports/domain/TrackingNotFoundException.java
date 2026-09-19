package com.trackflow.modules.reports.domain;

public class TrackingNotFoundException extends RuntimeException {

    public TrackingNotFoundException(String trackingNumber) {
        super("No se encontro un envio con el numero de seguimiento: " + trackingNumber);
    }
}