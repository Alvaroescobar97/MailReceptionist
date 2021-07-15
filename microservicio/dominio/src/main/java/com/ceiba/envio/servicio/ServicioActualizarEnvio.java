package com.ceiba.envio.servicio;

import com.ceiba.dominio.excepcion.ExcepcionDuplicidad;
import com.ceiba.envio.modelo.entidad.Envio;
import com.ceiba.envio.puerto.repositorio.RepositorioEnvio;

public class ServicioActualizarEnvio {

    private static final String EL_ENVIO_YA_EXISTE = "El envio ya existe en el sistema";

    private final RepositorioEnvio repositorioEnvio;

    public ServicioActualizarEnvio(RepositorioEnvio repositorioEnvio) {
        this.repositorioEnvio = repositorioEnvio;
    }

    public void ejecutar(Envio envio){
        //validarExistenciaPrevia(envio);
        this.repositorioEnvio.actualizar(envio);
    }

    private void validarExistenciaPrevia(Envio envio) {
        boolean exite = repositorioEnvio.existePorId(envio.getId());
        if(exite){
            throw new ExcepcionDuplicidad(EL_ENVIO_YA_EXISTE);
        }
    }

}
