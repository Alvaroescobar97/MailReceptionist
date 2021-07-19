package com.ceiba.envio.servicio.testdatabuilder;

import com.ceiba.envio.comando.ComandoEnvio;

import java.time.LocalDateTime;

public class ComandoEnvioTestDataBuilder {

    private Long id;
    private String cedulaEmisor;
    private String cedulaReceptor;
    private LocalDateTime fecha;
    private String tipo;
    private Double peso;
    private Double valor;

    public ComandoEnvioTestDataBuilder(){
        cedulaEmisor = "0987654321";
        cedulaReceptor = "1234567890";
        tipo = "PAQUETE";
        peso = 20.3;
        valor = 40500.0;
    }
    public ComandoEnvioTestDataBuilder conFecha(LocalDateTime fecha){
        this.fecha = fecha;
        return this;
    }
    public ComandoEnvio build(){
        return new ComandoEnvio(id,cedulaEmisor,cedulaReceptor,fecha,tipo,peso,valor);
    }
}
