package com.ceiba.usuario.puerto.dao;

import com.ceiba.usuario.modelo.dto.DtoEnvio;

import java.util.List;

public interface DaoEnvio {

    /**
     * Permite listar envios
     * @return los envios
     */
    List<DtoEnvio> listar();
}
