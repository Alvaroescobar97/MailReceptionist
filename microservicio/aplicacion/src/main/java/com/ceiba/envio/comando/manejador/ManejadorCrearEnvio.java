package com.ceiba.envio.comando.manejador;

import com.ceiba.ComandoRespuesta;
import com.ceiba.envio.comando.ComandoEnvio;
import com.ceiba.envio.comando.fabrica.FabricaEnvio;
import com.ceiba.envio.modelo.entidad.Envio;
import com.ceiba.envio.servicio.ServicioCrearEnvio;
import com.ceiba.manejador.ManejadorComandoRespuesta;
import org.springframework.stereotype.Component;

@Component
public class ManejadorCrearEnvio implements ManejadorComandoRespuesta<ComandoEnvio, ComandoRespuesta<Long>> {

    private final FabricaEnvio fabricaEnvio;
    private final ServicioCrearEnvio servicioCrearEnvio;

    public ManejadorCrearEnvio(FabricaEnvio fabricaEnvio, ServicioCrearEnvio servicioCrearEnvio) {
        this.fabricaEnvio = fabricaEnvio;
        this.servicioCrearEnvio = servicioCrearEnvio;
    }

    @Override
    public ComandoRespuesta<Long> ejecutar(ComandoEnvio comando) {
        Envio envio = this.fabricaEnvio.crear(comando);

        return new ComandoRespuesta<>(this.servicioCrearEnvio.ejecutar(envio));
    }
}
