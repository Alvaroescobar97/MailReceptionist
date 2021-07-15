package com.ceiba.envio.servicio.testdatabuilder;

import com.ceiba.envio.comando.ComandoEnvio;

import java.time.LocalDateTime;
import java.util.UUID;

public class ComandoEnvioTestDataBuilder {

    private Long id;
    private String cedulaEmisor;
    private String cedulaReceptor;
    private LocalDateTime fecha;
    private String tipo;
    private Double peso;
    private Double valor;

    public ComandoEnvioTestDataBuilder(){
        cedulaEmisor = UUID.randomUUID().toString();
        cedulaReceptor = UUID.randomUUID().toString();
        fecha = LocalDateTime.of(2021,07,15,0,0);
        tipo = "PAQUETE";
        peso = 20.3;
        valor = 40500.0;
    }

    public ComandoEnvio build(){
        return new ComandoEnvio(id,cedulaEmisor,cedulaReceptor,fecha,tipo,peso,valor);
    }
}
