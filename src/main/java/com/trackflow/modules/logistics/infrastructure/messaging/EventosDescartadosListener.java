package com.trackflow.modules.logistics.infrastructure.messaging;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class EventosDescartadosListener {

    private static final Logger log = LoggerFactory.getLogger(EventosDescartadosListener.class);

    @RabbitListener(queues = RabbitMQConfig.DEAD_LETTER_QUEUE)
    public void recibir(EventoLogisticoMensaje mensaje) {
        log.error("Evento logístico descartado: eventId={} trackingNumber={} tipo={} punto={} ocurridoEn={}",
                mensaje.eventId(), mensaje.trackingNumber(), mensaje.tipo(), mensaje.punto(), mensaje.ocurridoEn());
    }
}
