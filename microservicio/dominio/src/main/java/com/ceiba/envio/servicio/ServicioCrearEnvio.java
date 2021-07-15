package com.ceiba.envio.servicio;

import com.ceiba.dominio.excepcion.ExcepcionNegacionEnvio;
import com.ceiba.envio.modelo.entidad.Envio;
import com.ceiba.envio.puerto.repositorio.RepositorioEnvio;

import java.time.DayOfWeek;
import java.time.LocalDateTime;

public class ServicioCrearEnvio {

    private static final String NEGACION_ENVIO = "Los días Domingos no se reciben envios, vuelve otro día";
    private static final Double COSTO_ADICIONAL = 10000.0;

    private final RepositorioEnvio repositorioEnvio;

    public ServicioCrearEnvio(RepositorioEnvio repositorioEnvio) {
        this.repositorioEnvio = repositorioEnvio;
    }

    public Long ejecutar(Envio envio){
        validarNegacionEnvio(envio);
        envio = cobrarCostoAdicionalPorSerSabado(envio);
        return this.repositorioEnvio.crear(envio);
    }

    private Envio cobrarCostoAdicionalPorSerSabado(Envio envio){
         if(envio.getFecha().getDayOfWeek() == DayOfWeek.SATURDAY){
             Envio e = new Envio(envio.getId(), envio.getCedulaEmisor(), envio.getCedulaReceptor(), envio.getFecha(), envio.getTipo(), envio.getPeso(), envio.getValor() + COSTO_ADICIONAL);
            return e;
        }else {
            return envio;
        }
    }

    private void validarNegacionEnvio(Envio envio){
        if (envio.getFecha().getDayOfWeek() == DayOfWeek.SUNDAY){
            throw new ExcepcionNegacionEnvio(NEGACION_ENVIO);
        }
    }

}
