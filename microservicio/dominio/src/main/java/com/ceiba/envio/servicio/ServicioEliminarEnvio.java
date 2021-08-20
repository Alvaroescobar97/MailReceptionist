package com.ceiba.envio.servicio;

import com.ceiba.envio.puerto.repositorio.RepositorioEnvio;

public class ServicioEliminarEnvio {

    private RepositorioEnvio repositorioEnvio;

    public ServicioEliminarEnvio(RepositorioEnvio repositorioEnvio) {
        this.repositorioEnvio = repositorioEnvio;
    }

    public Boolean ejecutar(Long id){
        return this.repositorioEnvio.eliminar(id);
    }
}
