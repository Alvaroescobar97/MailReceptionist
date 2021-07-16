package com.ceiba.cliente.adaptador.dao;

import com.ceiba.cliente.modelo.dto.DtoCliente;
import com.ceiba.infraestructura.jdbc.MapperResult;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class MapeoCliente implements RowMapper<DtoCliente>, MapperResult {


    @Override
    public DtoCliente mapRow(ResultSet resultSet, int rowNum) throws SQLException {

        String cedula = resultSet.getString("cedula");
        String nombre= resultSet.getString("nombre");
        String direccion= resultSet.getString("direccion");
        Long telefono = resultSet.getLong("telefono");
        String ciudad= resultSet.getString("ciudad");

        return new DtoCliente(cedula,nombre,direccion,telefono,ciudad);
    }
}
