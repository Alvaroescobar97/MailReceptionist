package com.ceiba.envio.puerto.dao;

import com.ceiba.envio.modelo.dto.DtoEnvio;

import java.util.List;

public interface DaoEnvio {

    /**
     * Permite listar envios
     * @return los envios
     */
    List<DtoEnvio> listar();
}
