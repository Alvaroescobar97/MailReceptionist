package com.ceiba.envio.modelo.entidad;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class Envio {

    public static final String PAQUETE = "PAQUETE";
    public static final String CARTA = "CARTA";

    private Long id;
    private String cedulaEmisor;
    private String cedulaReceptor;
    private LocalDateTime fecha;
    private String tipo;
    private Double peso;
    private Double valor;

    public Envio(Long id,String cedulaEmisor, String cedulaReceptor, LocalDateTime fecha, String tipo, Double peso, Double valor) {
        this.id = id;
        this.cedulaEmisor = cedulaEmisor;
        this.cedulaReceptor = cedulaReceptor;
        this.fecha = fecha;
        this.tipo = tipo;
        this.peso = peso;
        this.valor = valor;
    }


}
