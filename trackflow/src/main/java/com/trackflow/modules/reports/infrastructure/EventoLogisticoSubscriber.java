package com.trackflow.modules.reports.infrastructure;

import com.trackflow.modules.reports.application.ProyectarSeguimientoEnvio;
import com.trackflow.shared.events.EventoLogisticoRegistradoEvent;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
public class EventoLogisticoSubscriber {

    private final ProyectarSeguimientoEnvio proyeccion;

    public EventoLogisticoSubscriber(ProyectarSeguimientoEnvio proyeccion) {
        this.proyeccion = proyeccion;
    }

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void on(EventoLogisticoRegistradoEvent event) {
        proyeccion.alRegistrarEvento(event);
    }
}