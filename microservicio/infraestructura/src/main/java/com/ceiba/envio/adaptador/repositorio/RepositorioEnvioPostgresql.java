package com.ceiba.envio.adaptador.repositorio;

import com.ceiba.envio.modelo.entidad.Envio;
import com.ceiba.envio.puerto.repositorio.RepositorioEnvio;
import com.ceiba.infraestructura.jdbc.CustomNamedParameterJdbcTemplate;
import com.ceiba.infraestructura.jdbc.sqlstatement.SqlStatement;
import org.springframework.stereotype.Repository;

@Repository
public class RepositorioEnvioPostgresql implements RepositorioEnvio {

    private final CustomNamedParameterJdbcTemplate customNamedParameterJdbcTemplate;

    @SqlStatement(namespace="envio", value="crear")
    private static String sqlCrear;

    @SqlStatement(namespace="envio", value="actualizar")
    private static String sqlActualizar;

    public RepositorioEnvioPostgresql(CustomNamedParameterJdbcTemplate customNamedParameterJdbcTemplate) {
        this.customNamedParameterJdbcTemplate = customNamedParameterJdbcTemplate;
    }

    @Override
    public Long crear(Envio envio) {
        return this.customNamedParameterJdbcTemplate.crear(envio, sqlCrear);
    }

    @Override
    public void actualizar(Envio envio) {
        this.customNamedParameterJdbcTemplate.crear(envio, sqlActualizar);
    }

    @Override
    public void eliminar(Long id) {

    }

    @Override
    public boolean existePorId(Long id) {
        return false;
    }
}
