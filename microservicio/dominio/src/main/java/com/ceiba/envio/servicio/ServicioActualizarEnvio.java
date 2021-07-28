package com.ceiba.envio.servicio;

import com.ceiba.cliente.puerto.repositorio.RepositorioCliente;
import com.ceiba.dominio.excepcion.*;
import com.ceiba.envio.modelo.entidad.Envio;
import com.ceiba.envio.puerto.repositorio.RepositorioEnvio;

public class ServicioActualizarEnvio {

    private static final String EL_ENVIO_YA_EXISTE = "El envio que desea modificar no existe en el sistema";
    public static final String NEGACION_ENVIO = "Los días Domingos no se reciben envios, por favor vuelva otro día";
    public static final String TIPO_ENVIO_ERRONEO = "Solo se aceptan envios de CARTAS o PAQUETES";
    public static final String PESO_PAQUETE_INVALIDO = "Los PAQUETES deben tener peso";
    public static final String PESO_CARTA_INVALIDO = "Las CARTAS NO deben tener peso, es decir peso = 0";
    public static final String CEDULA_EMISOR_INVALIDA = "El EMISOR del correo NO existe en el sistema, debe registrarse primero";
    public static final String CEDULA_RECEPTOR_INVALIDA = "El RECEPTOR del correo NO existe en el sistema, debe registrarse primero";

    public static final Double COSTO_ADICIONAL = 10000.0;

    private final RepositorioEnvio repositorioEnvio;
    private final RepositorioCliente repositorioCliente;

    public ServicioActualizarEnvio(RepositorioEnvio repositorioEnvio, RepositorioCliente repositorioCliente) {
        this.repositorioEnvio = repositorioEnvio;
        this.repositorioCliente = repositorioCliente;
    }

    public void ejecutar(Envio envio){
        validarExistenciaPrevia(envio);
        validarExistenciaEmisorEnvio(envio);
        validarExistenciaReceptorEnvio(envio);

        envio.cobrarCostoAdicionalPorSerSabado();
        envio.validarTipoDeEnvio();
        envio.validarPesoDependiendoDelTipo();
        envio.validarNegacionEnvio();

        this.repositorioEnvio.actualizar(envio);
    }

    private void validarExistenciaPrevia(Envio envio) {
        boolean exite = repositorioEnvio.existePorId(envio.getId());
        if(!exite){
            throw new ExcepcionDuplicidad(EL_ENVIO_YA_EXISTE);
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
