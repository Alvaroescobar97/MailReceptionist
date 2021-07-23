package com.ceiba.envio.modelo.entidad;

import com.ceiba.dominio.ValidadorArgumento;
import com.ceiba.dominio.excepcion.ExcepcionNegacionEnvio;
import com.ceiba.dominio.excepcion.ExcepcionPesoInvalido;
import com.ceiba.dominio.excepcion.ExcepcionTipoEnvio;
import lombok.Getter;
import lombok.Setter;

import java.time.DayOfWeek;
import java.time.LocalDateTime;

public class Envio {

    public static final Double COSTO_ADICIONAL = 11000.0;
    public static final String NEGACION_ENVIO = "Los días Domingos no se reciben envios, por favor vuelva otro día";
    public static final String TIPO_ENVIO_ERRONEO = "Solo se aceptan envios de CARTAS o PAQUETES";
    public static final String PESO_PAQUETE_INVALIDO = "Los PAQUETES deben tener peso";
    public static final String PESO_CARTA_INVALIDO = "Las CARTAS NO deben tener peso, es decir peso = 0";
    public static final String TIPO_OBLIGATORIO = "El tipo del envio es obligatorio";
    public static final String PESO_OBLIGATORIO = "El peso del envio es obligatorio";
    public static final String VALOR_OBLIGATORIO = "El valor del envio es obligatorio";
    public static final String FECHA_OBLIGATORIA = "La fecha del envio es obligatoria";

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
        ValidadorArgumento.validarObligatorio(fecha,FECHA_OBLIGATORIA);
        ValidadorArgumento.validarObligatorio(tipo,TIPO_OBLIGATORIO);
        ValidadorArgumento.validarObligatorio(peso,PESO_OBLIGATORIO);
        ValidadorArgumento.validarObligatorio(valor,VALOR_OBLIGATORIO);
        this.id = id;
        this.cedulaEmisor = cedulaEmisor;
        this.cedulaReceptor = cedulaReceptor;
        this.fecha = fecha;
        this.tipo = tipo;
        this.peso = peso;
        this.valor = valor;
    }

    public Long getId() {
        return id;
    }

    public String getCedulaEmisor() {
        return cedulaEmisor;
    }

    public String getCedulaReceptor() {
        return cedulaReceptor;
    }

    public LocalDateTime getFecha() {
        return fecha;
    }

    public Double getValor() {
        return valor;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }

    public void cobrarCostoAdicionalPorSerSabado(){
        if(this.fecha.getDayOfWeek() == DayOfWeek.SATURDAY){
            this.setValor(this.valor+COSTO_ADICIONAL);
        }
    }

    public void validarTipoDeEnvio(){
        if (!this.tipo.equals(Envio.PAQUETE) && !this.tipo.equals(Envio.CARTA)){
            throw new ExcepcionTipoEnvio(TIPO_ENVIO_ERRONEO);
        }
    }

    public void validarPesoDependiendoDelTipo(){
        if (this.tipo.equals(Envio.PAQUETE) && this.peso<=0){
            throw new ExcepcionPesoInvalido(PESO_PAQUETE_INVALIDO);
        }else if(this.tipo.equals(Envio.CARTA) && this.peso>0){
            throw new ExcepcionPesoInvalido(PESO_CARTA_INVALIDO);
        }
    }

    public void validarNegacionEnvio(){
        if (this.fecha.getDayOfWeek() == DayOfWeek.SUNDAY){
            throw new ExcepcionNegacionEnvio(NEGACION_ENVIO);
        }
    }

}
