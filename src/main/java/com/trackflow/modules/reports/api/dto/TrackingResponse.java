package com.trackflow.modules.reports.api.dto;

import java.time.Instant;

public record TrackingResponse(
        String trackingNumber,
        String status,
        String recipientName,
        String destinationCity,
        Instant registeredAt,
        String lastMovementPoint,
        Instant lastMovementAt) {
}