package com.trackflow.modules.reports.infrastructure;

import com.trackflow.modules.reports.application.ProyectarSeguimientoEnvio;
import com.trackflow.shared.events.EnvioCreadoEvent;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
public class EnvioCreadoSubscriber {

    private final ProyectarSeguimientoEnvio proyeccion;

    public EnvioCreadoSubscriber(ProyectarSeguimientoEnvio proyeccion) {
        this.proyeccion = proyeccion;
    }

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void on(EnvioCreadoEvent event) {
        proyeccion.alCrearEnvio(event);
    }
}