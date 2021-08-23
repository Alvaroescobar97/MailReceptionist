package com.ceiba.envio.servicio;

import com.ceiba.dominio.excepcion.ExcepcionValorInvalido;
import com.ceiba.envio.puerto.repositorio.RepositorioEnvio;

public class ServicioEliminarEnvio {

    private static final String EL_ENVIO_NO_EXISTE = "El envio que desea eliminar NO existe en el sistema";

    private RepositorioEnvio repositorioEnvio;

    public ServicioEliminarEnvio(RepositorioEnvio repositorioEnvio) {
        this.repositorioEnvio = repositorioEnvio;
    }

    public Boolean ejecutar(Long id){
        validarExistenciaPrevia(id);

        return this.repositorioEnvio.eliminar(id);
    }

    private void validarExistenciaPrevia(Long id) {
        boolean exite = repositorioEnvio.existePorId(id);
        if(!exite){
            throw new ExcepcionValorInvalido(EL_ENVIO_NO_EXISTE);
        }
    }
}
