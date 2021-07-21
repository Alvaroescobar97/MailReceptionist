package com.ceiba.envio.servicio;

import com.ceiba.cliente.puerto.repositorio.RepositorioCliente;
import com.ceiba.dominio.ValidadorArgumento;
import com.ceiba.dominio.excepcion.*;
import com.ceiba.envio.modelo.entidad.Envio;
import com.ceiba.envio.puerto.repositorio.RepositorioEnvio;

import java.time.DayOfWeek;
import java.time.LocalDateTime;


public class ServicioCrearEnvio {

    public static final String NEGACION_ENVIO = "Los días Domingos no se reciben envios, por favor vuelva otro día";
    public static final String TIPO_ENVIO_ERRONEO = "Solo se aceptan envios de CARTAS o PAQUETES";
    public static final String PESO_PAQUETE_INVALIDO = "Los PAQUETES deben tener peso";
    public static final String PESO_CARTA_INVALIDO = "Las CARTAS NO deben tener peso, es decir peso = 0";
    public static final String FECHA_OBLIGATORIA = "La fecha del envio es obligatoria";
    public static final String CEDULA_EMISOR_INVALIDA = "El EMISOR del correo NO existe en el sistema, debe registrarse primero";
    public static final String CEDULA_RECEPTOR_INVALIDA = "El RECEPTOR del correo NO existe en el sistema, debe registrarse primero";
    public static final String ULTIMO_ENVIO_PENDIENTE = "Ultimo envio pendiente por ser entregado, debe esperar a que la entrega se realice";
    public static final String TIPO_OBLIGATORIO = "El tipo del envio es obligatorio";
    public static final String PESO_OBLIGATORIO = "El peso del envio es obligatorio";
    public static final String VALOR_OBLIGATORIO = "El valor del envio es obligatorio";
    public static final Double COSTO_ADICIONAL = 11000.0;
    public static final Integer DIAS_ENTREGA_ENVIO = 5;

    private final RepositorioEnvio repositorioEnvio;
    private final RepositorioCliente repositorioCliente;

    public ServicioCrearEnvio(RepositorioEnvio repositorioEnvio, RepositorioCliente repositorioCliente) {
        this.repositorioEnvio = repositorioEnvio;
        this.repositorioCliente = repositorioCliente;
    }

    public Long ejecutar(Envio envio){
        ValidadorArgumento.validarObligatorio(envio.getFecha(),FECHA_OBLIGATORIA);
        ValidadorArgumento.validarObligatorio(envio.getTipo(),TIPO_OBLIGATORIO);
        ValidadorArgumento.validarObligatorio(envio.getPeso(),PESO_OBLIGATORIO);
        ValidadorArgumento.validarObligatorio(envio.getValor(),VALOR_OBLIGATORIO);
        validarEntregaUltimoEnvioClienteEmisor(envio);
        validarExistenciaEmisorEnvio(envio);
        validarExistenciaReceptorEnvio(envio);
        validarNegacionEnvio(envio);
        validarTipoDeEnvio(envio);
        validarPesoDependiendoDelTipo(envio);
        cobrarCostoAdicionalPorSerSabado(envio);

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

    private void cobrarCostoAdicionalPorSerSabado(Envio envio){
        if(envio.getFecha().getDayOfWeek() == DayOfWeek.SATURDAY){
            envio.setValor(envio.getValor()+COSTO_ADICIONAL);
        }
    }

    private void validarTipoDeEnvio(Envio envio){
        if (!envio.getTipo().equals(Envio.PAQUETE) && !envio.getTipo().equals(Envio.CARTA)){
            throw new ExcepcionTipoEnvio(TIPO_ENVIO_ERRONEO);
        }
    }

    private void validarPesoDependiendoDelTipo(Envio envio){
        if (envio.getTipo().equals(Envio.PAQUETE) && envio.getPeso()<=0){
            throw new ExcepcionPesoInvalido(PESO_PAQUETE_INVALIDO);
        }else if(envio.getTipo().equals(Envio.CARTA) && envio.getPeso()>0){
            throw new ExcepcionPesoInvalido(PESO_CARTA_INVALIDO);
        }
    }

    private void validarNegacionEnvio(Envio envio){
        if (envio.getFecha().getDayOfWeek() == DayOfWeek.SUNDAY){
            throw new ExcepcionNegacionEnvio(NEGACION_ENVIO);
        }
    }
}
