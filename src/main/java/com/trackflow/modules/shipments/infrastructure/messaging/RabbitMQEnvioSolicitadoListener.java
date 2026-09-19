package com.trackflow.modules.shipments.infrastructure.messaging;

import com.trackflow.modules.shipments.application.RegistrarEnvio;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class RabbitMQEnvioSolicitadoListener {

    private final RegistrarEnvio registrarEnvio;

    public RabbitMQEnvioSolicitadoListener(RegistrarEnvio registrarEnvio) {
        this.registrarEnvio = registrarEnvio;
    }

    @RabbitListener(queues = ShipmentsRabbitMQConfig.QUEUE)
    public void recibir(EnvioSolicitadoMensaje mensaje) {
        registrarEnvio.ejecutar(mensaje.toSolicitud());
    }
}
