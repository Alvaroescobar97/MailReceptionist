package com.ceiba.envio.adaptador.dao;

import com.ceiba.infraestructura.jdbc.CustomNamedParameterJdbcTemplate;
import com.ceiba.infraestructura.jdbc.sqlstatement.SqlStatement;
import com.ceiba.usuario.modelo.dto.DtoEnvio;
import com.ceiba.usuario.puerto.dao.DaoEnvio;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DaoEnvioPostgresql implements DaoEnvio {

    private final CustomNamedParameterJdbcTemplate customNamedParameterJdbcTemplate;

    @SqlStatement(namespace="envio", value="listar")
    private static String sqlListar;

    public DaoEnvioPostgresql(CustomNamedParameterJdbcTemplate customNamedParameterJdbcTemplate) {
        this.customNamedParameterJdbcTemplate = customNamedParameterJdbcTemplate;
    }


    @Override
    public List<DtoEnvio> listar() {
        return this.customNamedParameterJdbcTemplate.getNamedParameterJdbcTemplate().query(sqlListar, new MapeoEnvio());
    }
}
