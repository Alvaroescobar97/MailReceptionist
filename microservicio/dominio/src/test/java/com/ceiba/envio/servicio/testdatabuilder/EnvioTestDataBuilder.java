package com.ceiba.envio.servicio.testdatabuilder;

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
        peso = 0.0;
        valor = 35000.0;
    }

    public EnvioTestDataBuilder conFecha(LocalDateTime fecha){
        this.fecha = fecha;
        return this;
    }

    public EnvioTestDataBuilder conTipo(String tipo){
        this.tipo = tipo;
        return this;
    }

    public EnvioTestDataBuilder conPeso(Double peso){
        this.peso = peso;
        return this;
    }

    public Envio build(){
        return new Envio(id,cedulaEmisor,cedulaReceptor,fecha,tipo,peso,valor);
    }
}
