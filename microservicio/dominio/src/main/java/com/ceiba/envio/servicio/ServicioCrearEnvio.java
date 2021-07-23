package com.ceiba.envio.servicio;

import com.ceiba.cliente.puerto.repositorio.RepositorioCliente;
import com.ceiba.dominio.ValidadorArgumento;
import com.ceiba.dominio.excepcion.*;
import com.ceiba.envio.modelo.entidad.Envio;
import com.ceiba.envio.puerto.repositorio.RepositorioEnvio;

import java.time.DayOfWeek;
import java.time.LocalDateTime;


public class ServicioCrearEnvio {

    public static final String CEDULA_EMISOR_INVALIDA = "El EMISOR del correo NO existe en el sistema, debe registrarse primero";
    public static final String CEDULA_RECEPTOR_INVALIDA = "El RECEPTOR del correo NO existe en el sistema, debe registrarse primero";
    public static final String ULTIMO_ENVIO_PENDIENTE = "Ultimo envio pendiente por ser entregado, debe esperar a que la entrega se realice";


    public static final Integer DIAS_ENTREGA_ENVIO = 5;

    private final RepositorioEnvio repositorioEnvio;
    private final RepositorioCliente repositorioCliente;

    public ServicioCrearEnvio(RepositorioEnvio repositorioEnvio, RepositorioCliente repositorioCliente) {
        this.repositorioEnvio = repositorioEnvio;
        this.repositorioCliente = repositorioCliente;
    }

    public Long ejecutar(Envio envio){
        validarEntregaUltimoEnvioClienteEmisor(envio);
        validarExistenciaEmisorEnvio(envio);
        validarExistenciaReceptorEnvio(envio);
        envio.cobrarCostoAdicionalPorSerSabado();
        envio.validarTipoDeEnvio();
        envio.validarPesoDependiendoDelTipo();
        envio.validarNegacionEnvio();

        return this.repositorioEnvio.crear(envio);
    }

    private void validarEntregaUltimoEnvioClienteEmisor(Envio envio) {
        LocalDateTime fechaUltimoEnvio = this.repositorioEnvio.ultimoEnvioClienteEmisor(envio.getCedulaEmisor());
        if(fechaUltimoEnvio !=null && fechaUltimoEnvio != LocalDateTime.MIN){
            int dias =1;
            while (dias<=DIAS_ENTREGA_ENVIO){
                fechaUltimoEnvio = fechaUltimoEnvio.plusDays(1);
                if(fechaUltimoEnvio.isAfter(envio.getFecha()) || fechaUltimoEnvio.isEqual(envio.getFecha())){
                    throw new ExcepcionUltimoEnvioPendiente(ULTIMO_ENVIO_PENDIENTE);
                }
                if (fechaUltimoEnvio.getDayOfWeek() != DayOfWeek.SATURDAY && fechaUltimoEnvio.getDayOfWeek() != DayOfWeek.SUNDAY){
                    dias++;
                }
            }
        }
    }

    private void validarExistenciaEmisorEnvio(Envio envio) {
        boolean existeEmisor = this.repositorioCliente.existePorCedula(envio.getCedulaEmisor());
        if(!existeEmisor){
            throw new ExcepcionValorInvalido(CEDULA_EMISOR_INVALIDA);
        }
    }

    private void validarExistenciaReceptorEnvio(Envio envio) {
        boolean existeReceptor = this.repositorioCliente.existePorCedula(envio.getCedulaReceptor());
        if(!existeReceptor){
            throw new ExcepcionValorInvalido(CEDULA_RECEPTOR_INVALIDA);
        }
    }


}
