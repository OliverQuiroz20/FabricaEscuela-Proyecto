package com.trackflow.bootstrap;

import com.trackflow.modules.logistics.application.RepublicarEventosLogisticos;
import com.trackflow.modules.shipments.application.RepublicarEnviosCreados;
import java.util.Map;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/admin")
public class ReconstruccionController {

    private final RepublicarEnviosCreados envios;
    private final RepublicarEventosLogisticos eventos;

    public ReconstruccionController(RepublicarEnviosCreados envios, RepublicarEventosLogisticos eventos) {
        this.envios = envios;
        this.eventos = eventos;
    }

    @PostMapping("/reconstruir-proyecciones")
    public Map<String, Integer> reconstruir() {
        int enviosRepublicados = envios.ejecutar();
        int eventosRepublicados = eventos.ejecutar();

        return Map.of(
                "enviosRepublicados", enviosRepublicados,
                "eventosRepublicados", eventosRepublicados);
    }
}
