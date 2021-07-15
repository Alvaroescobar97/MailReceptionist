package com.ceiba.usuario.servicio.testdatabuilder;

import com.ceiba.envio.modelo.entidad.Envio;

import java.time.LocalDateTime;

public class EnvioTestDataBuilder {

    private Long id;
    private String cedulaEmisor;
    private String cedulaReceptor;
    private LocalDateTime fecha;
    private String tipo;
    private Double peso;
    private Double valor;

    public EnvioTestDataBuilder(){
        cedulaEmisor ="543210";
        cedulaReceptor ="012345";
        tipo ="PAQUETE";
        peso = 20.3;
        valor = 35000.0;
    }

    public EnvioTestDataBuilder conFecha(LocalDateTime fecha){
        this.fecha = fecha;
        return this;
    }

    public Envio build(){
        return new Envio(id,cedulaEmisor,cedulaReceptor,fecha,tipo,peso,valor);
    }
}
