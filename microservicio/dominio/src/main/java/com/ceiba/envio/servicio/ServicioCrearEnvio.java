package com.ceiba.envio.servicio;

import com.ceiba.cliente.puerto.repositorio.RepositorioCliente;
import com.ceiba.dominio.ValidadorArgumento;
import com.ceiba.dominio.excepcion.*;
import com.ceiba.envio.modelo.entidad.Envio;
import com.ceiba.envio.puerto.repositorio.RepositorioEnvio;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.util.Calendar;

public class ServicioCrearEnvio {

    public static final String NEGACION_ENVIO = "Los días Domingos no se reciben envios, por favor vuelva otro día";
    public static final String TIPO_ENVIO_ERRONEO = "Solo se aceptan envios de CARTAS o PAQUETES";
    public static final String PESO_PAQUETE_INVALIDO = "Los PAQUETES deben tener peso";
    public static final String PESO_CARTA_INVALIDO = "Las CARTAS NO deben tener peso, es decir peso = 0";
    public static final String FECHA_OBLIGATORIA = "La fecha de creacion es obligatoria";
    public static final String CEDULA_EMISOR_INVALIDA = "El EMISOR del correo NO existe en el sistema, debe registrarse primero";
    public static final String CEDULA_RECEPTOR_INVALIDA = "El RECEPTOR del correo NO existe en el sistema, debe registrarse primero";
    public static final Double COSTO_ADICIONAL = 10000.0;

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
        validarNegacionEnvio(envio);
        validarTipoDeEnvio(envio);
        validarPesoDependiendoDelTipo(envio);
        ValidadorArgumento.validarObligatorio(envio.getFecha(),FECHA_OBLIGATORIA);
        cobrarCostoAdicionalPorSerSabado(envio);

        return this.repositorioEnvio.crear(envio);
    }

    public void validarEntregaUltimoEnvioClienteEmisor(Envio envio) {
        LocalDateTime fechaUltimoEnvio = this.repositorioEnvio.ultimoEnvioClienteEmisor(envio.getCedulaEmisor());

        /*
        //if(fechaUltimoEnvio != null && fechaUltimoEnvio!= LocalDateTime.MIN && fechaUltimoEnvio.isBefore(LocalDateTime.now()) && !fechaUltimoEnvio.isEqual(LocalDateTime.now())){
            System.out.println(fechaUltimoEnvio);
            int dias =0;

            while (dias<=5){
                fechaUltimoEnvio = fechaUltimoEnvio.plusDays(1);
                System.out.println(fechaUltimoEnvio);
                if (fechaUltimoEnvio.getDayOfWeek() != DayOfWeek.SATURDAY && fechaUltimoEnvio.getDayOfWeek() != DayOfWeek.SUNDAY){

                }
                dias++;
            }
            if(fechaUltimoEnvio.isAfter(LocalDateTime.now())){
                throw new ExcepcionUltimoEnvioPendiente("Ultimo envio pendiente por ser entregado, debe esperar a que la entrega se realice");
            }

        //}*/
    }

    public void validarExistenciaEmisorEnvio(Envio envio) {
        boolean existeEmisor = this.repositorioCliente.existePorCedula(envio.getCedulaEmisor());
        if(!existeEmisor){
            throw new ExcepcionValorInvalido(CEDULA_EMISOR_INVALIDA);
        }
    }

    public void validarExistenciaReceptorEnvio(Envio envio) {
        boolean existeReceptor = this.repositorioCliente.existePorCedula(envio.getCedulaEmisor());
        if(!existeReceptor){
            throw new ExcepcionValorInvalido(CEDULA_RECEPTOR_INVALIDA);
        }
    }

    public void cobrarCostoAdicionalPorSerSabado(Envio envio){
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
        if (envio.getTipo().equals(Envio.PAQUETE) && envio.getPeso()<=0){
            throw new ExcepcionPesoInvalido(PESO_PAQUETE_INVALIDO);
        }else if(envio.getTipo().equals(Envio.CARTA) && envio.getPeso()>0){
            throw new ExcepcionPesoInvalido(PESO_CARTA_INVALIDO);
        }
    }

    public void validarNegacionEnvio(Envio envio){
        if (envio.getFecha().getDayOfWeek() == DayOfWeek.SUNDAY){
            throw new ExcepcionNegacionEnvio(NEGACION_ENVIO);
        }
    }

}
