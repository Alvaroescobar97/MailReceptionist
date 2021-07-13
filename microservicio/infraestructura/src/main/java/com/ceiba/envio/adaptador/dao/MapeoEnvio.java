package com.ceiba.envio.adaptador.dao;

import com.ceiba.infraestructura.jdbc.MapperResult;
import com.ceiba.envio.modelo.dto.DtoEnvio;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;

public class MapeoEnvio implements RowMapper<DtoEnvio>, MapperResult {

    @Override
    public DtoEnvio mapRow(ResultSet resultSet, int rowNum) throws SQLException {

        Long id = resultSet.getLong("id");
        String cedulaEmisor = resultSet.getString("cedulaEmisor");
        String cedulaReceptor = resultSet.getString("cedulaReceptor");
        LocalDateTime fecha = extraerLocalDateTime(resultSet, "fecha");
        String tipo = resultSet.getString("tipo");
        Double peso = resultSet.getDouble("peso");
        Double valor= resultSet.getDouble("valor");

        return new DtoEnvio(id,cedulaEmisor,cedulaReceptor,fecha,tipo,peso,valor);
    }
}
