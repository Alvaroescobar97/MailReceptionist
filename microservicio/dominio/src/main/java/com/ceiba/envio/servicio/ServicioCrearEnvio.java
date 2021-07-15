package com.ceiba.envio.servicio;

import com.ceiba.dominio.ValidadorArgumento;
import com.ceiba.dominio.excepcion.ExcepcionNegacionEnvio;
import com.ceiba.dominio.excepcion.ExcepcionPesoInvalido;
import com.ceiba.dominio.excepcion.ExcepcionTipoEnvio;
import com.ceiba.envio.modelo.entidad.Envio;
import com.ceiba.envio.puerto.repositorio.RepositorioEnvio;

import java.time.DayOfWeek;

public class ServicioCrearEnvio {

    public static final String NEGACION_ENVIO = "Los días Domingos no se reciben envios, por favor vuelva otro día";
    public static final String TIPO_ENVIO_ERRONEO = "Solo se aceptan envios de CARTAS o PAQUETES";
    public static final String PESO_INVALIDO = "Los PAQUETES deben tener peso, las CARTAS deberian tener peso 0";
    public static final String FECHA_OBLIGATORIA = "La fecha de creacion es obligatoria";
    public static final Double COSTO_ADICIONAL = 10000.0;

    private final RepositorioEnvio repositorioEnvio;

    public ServicioCrearEnvio(RepositorioEnvio repositorioEnvio) {
        this.repositorioEnvio = repositorioEnvio;
    }

    public Long ejecutar(Envio envio){
        validarNegacionEnvio(envio);
        validarTipoDeEnvio(envio);
        validarPesoDependiendoDelTipo(envio);
        ValidadorArgumento.validarObligatorio(envio.getFecha(),FECHA_OBLIGATORIA);
        cobrarCostoAdicionalPorSerSabado(envio);
        return this.repositorioEnvio.crear(envio);
    }

    private void cobrarCostoAdicionalPorSerSabado(Envio envio){
        if(envio.getFecha().getDayOfWeek() == DayOfWeek.SATURDAY){
            envio.setValor(envio.getValor()+COSTO_ADICIONAL);
        }
    }

    public void validarTipoDeEnvio(Envio envio){
        if (!envio.getTipo().equals(Envio.PAQUETE) && !envio.getTipo().equals(Envio.CARTA)){
            throw new ExcepcionTipoEnvio(TIPO_ENVIO_ERRONEO);
        }
    }

    public void validarPesoDependiendoDelTipo(Envio envio){
        if ((envio.getTipo().equals(Envio.PAQUETE) && envio.getPeso()<=0) || (envio.getTipo().equals(Envio.CARTA) && envio.getPeso()>0)){
            throw new ExcepcionPesoInvalido(PESO_INVALIDO);
        }
    }

    private void validarNegacionEnvio(Envio envio){
        if (envio.getFecha().getDayOfWeek() == DayOfWeek.SUNDAY){
            throw new ExcepcionNegacionEnvio(NEGACION_ENVIO);
        }
    }

}
