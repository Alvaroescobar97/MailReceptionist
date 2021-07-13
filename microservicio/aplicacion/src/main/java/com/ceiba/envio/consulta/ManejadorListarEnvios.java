package com.ceiba.envio.consulta;

import com.ceiba.envio.modelo.dto.DtoEnvio;
import com.ceiba.envio.puerto.DaoEnvio;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ManejadorListarEnvios {
    private final DaoEnvio daoEnvio;

    public ManejadorListarEnvios(DaoEnvio daoEnvio) {
        this.daoEnvio = daoEnvio;
    }

    public List<DtoEnvio> ejecutar(){return this.daoEnvio.listar();}
}
