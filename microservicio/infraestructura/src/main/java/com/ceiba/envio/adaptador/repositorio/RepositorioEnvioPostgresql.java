package com.ceiba.envio.adaptador.repositorio;

import com.ceiba.envio.modelo.entidad.Envio;
import com.ceiba.envio.puerto.repositorio.RepositorioEnvio;
import com.ceiba.infraestructura.jdbc.CustomNamedParameterJdbcTemplate;
import com.ceiba.infraestructura.jdbc.sqlstatement.SqlStatement;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public class RepositorioEnvioPostgresql implements RepositorioEnvio {

    private final CustomNamedParameterJdbcTemplate customNamedParameterJdbcTemplate;

    @SqlStatement(namespace="envio", value="crear")
    private static String sqlCrear;

    @SqlStatement(namespace="envio", value="actualizar")
    private static String sqlActualizar;

    @SqlStatement(namespace="envio", value="existe")
    private static String sqlExiste;

    @SqlStatement(namespace="envio", value="ultimoEnvioClienteEmisor")
    private static String sqlUltimoEnvioClienteEmisor;

    public RepositorioEnvioPostgresql(CustomNamedParameterJdbcTemplate customNamedParameterJdbcTemplate) {
        this.customNamedParameterJdbcTemplate = customNamedParameterJdbcTemplate;
    }

    @Override
    public Long crear(Envio envio) {
        return this.customNamedParameterJdbcTemplate.crear(envio, sqlCrear);
    }

    @Override
    public void actualizar(Envio envio) {
        this.customNamedParameterJdbcTemplate.actualizar(envio, sqlActualizar);
    }

    @Override
    public void eliminar(Long id) {
        // Do nothing because only where required two services
    }

    @Override
    public boolean existePorId(Long id) {
        MapSqlParameterSource paramSource = new MapSqlParameterSource();
        paramSource.addValue("id", id);

        return this.customNamedParameterJdbcTemplate.getNamedParameterJdbcTemplate().queryForObject(sqlExiste,paramSource, Boolean.class);
    }

    @Override
    public LocalDateTime ultimoEnvioClienteEmisor(String cedulaEmisor) {
        MapSqlParameterSource paramSource = new MapSqlParameterSource();
        paramSource.addValue("cedulaEmisor", cedulaEmisor);
        try {
            LocalDateTime fecha = this.customNamedParameterJdbcTemplate.getNamedParameterJdbcTemplate().queryForObject(sqlUltimoEnvioClienteEmisor, paramSource, LocalDateTime.class);
            if( fecha == null){
                return LocalDateTime.MIN;
            }else{
                return fecha;
            }
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }
}
