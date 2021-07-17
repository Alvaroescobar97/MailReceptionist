package com.ceiba.cliente.servicio.testdatabuilder;

import com.ceiba.cliente.modelo.entidad.Cliente;
import com.ceiba.envio.modelo.entidad.Envio;
import com.ceiba.envio.servicio.testdatabuilder.EnvioTestDataBuilder;

import java.time.LocalDateTime;

public class ClienteTestDataBuilder {

    private String cedula;
    private String nombre;
    private String direccion;
    private Long telefono;
    private String ciudad;

    public ClienteTestDataBuilder() {
    }

    public ClienteTestDataBuilder conCedula(String cedula){
        this.cedula = cedula;
        return this;
    }

    public ClienteTestDataBuilder conNombre(String nombre){
        this.nombre = nombre;
        return this;
    }

    public ClienteTestDataBuilder conDireccion(String direccion){
        this.direccion = direccion;
        return this;
    }

    public ClienteTestDataBuilder conTelefono(Long telefono){
        this.telefono = telefono;
        return this;
    }

    public ClienteTestDataBuilder conCiudad(String ciudad){
        this.ciudad = ciudad;
        return this;
    }

    public Cliente build(){
        return new Cliente(cedula,nombre,direccion,telefono,ciudad);
    }
}
