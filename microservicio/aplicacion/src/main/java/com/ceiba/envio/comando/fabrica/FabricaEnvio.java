package com.ceiba.envio.comando.fabrica;

import com.ceiba.envio.comando.ComandoEnvio;
import com.ceiba.envio.modelo.entidad.Envio;
import org.springframework.stereotype.Component;

@Component
public class FabricaEnvio {

    public Envio crear(ComandoEnvio comandoEnvio){
        return new Envio(
                comandoEnvio.getId(),
                comandoEnvio.getCedulaEmisor(),
                comandoEnvio.getCedulaReceptor(),
                comandoEnvio.getFecha(),
                comandoEnvio.getTipo(),
                comandoEnvio.getPeso(),
                comandoEnvio.getValor()
        );
    }
}
