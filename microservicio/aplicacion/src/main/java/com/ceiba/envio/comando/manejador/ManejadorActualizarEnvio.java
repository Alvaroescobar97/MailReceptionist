package com.ceiba.envio.comando.manejador;

import com.ceiba.envio.comando.ComandoEnvio;
import com.ceiba.envio.comando.fabrica.FabricaEnvio;
import com.ceiba.envio.modelo.entidad.Envio;
import com.ceiba.envio.servicio.ServicioActualizarEnvio;
import com.ceiba.manejador.ManejadorComando;
import org.springframework.stereotype.Component;

@Component
public class ManejadorActualizarEnvio implements ManejadorComando<ComandoEnvio> {

    private final FabricaEnvio fabricaEnvio;
    private final ServicioActualizarEnvio servicioActualizarEnvio;

    public ManejadorActualizarEnvio(FabricaEnvio fabricaEnvio, ServicioActualizarEnvio servicioActualizarEnvio) {
        this.fabricaEnvio = fabricaEnvio;
        this.servicioActualizarEnvio = servicioActualizarEnvio;
    }

    public void ejecutar(ComandoEnvio comandoEnvio){
        Envio envio = this.fabricaEnvio.crear(comandoEnvio);
        this.servicioActualizarEnvio.ejecutar(envio);
    }

}
