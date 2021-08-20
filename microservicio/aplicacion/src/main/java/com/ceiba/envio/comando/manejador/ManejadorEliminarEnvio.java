package com.ceiba.envio.comando.manejador;

import com.ceiba.ComandoRespuesta;
import com.ceiba.envio.comando.ComandoEnvio;
import com.ceiba.envio.servicio.ServicioEliminarEnvio;
import com.ceiba.manejador.ManejadorComando;
import com.ceiba.manejador.ManejadorComandoRespuesta;
import org.springframework.stereotype.Component;

@Component
public class ManejadorEliminarEnvio implements ManejadorComandoRespuesta<Long, ComandoRespuesta<Boolean>> {

    private final ServicioEliminarEnvio servicioEliminarEnvio;

    public ManejadorEliminarEnvio(ServicioEliminarEnvio servicioEliminarEnvio) {
        this.servicioEliminarEnvio = servicioEliminarEnvio;
    }

    @Override
    public ComandoRespuesta<Boolean> ejecutar(Long id) {
        return new ComandoRespuesta<Boolean>(this.servicioEliminarEnvio.ejecutar(id));
    }
}
