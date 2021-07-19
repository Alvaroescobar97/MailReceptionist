package com.ceiba.cliente.servicio.testdatabuilder;

import com.ceiba.cliente.comando.ComandoCliente;

public class ComandoClienteTestDataBuilder {
    private String cedula;
    private String nombre;
    private String direccion;
    private Long telefono;
    private String ciudad;

    public ComandoClienteTestDataBuilder() {
    }

    public ComandoClienteTestDataBuilder conCedula(String cedula){
        this.cedula = cedula;
        return this;
    }

    public ComandoClienteTestDataBuilder conNombre(String nombre){
        this.nombre = nombre;
        return this;
    }

    public ComandoClienteTestDataBuilder conDireccion(String direccion){
        this.direccion = direccion;
        return this;
    }

    public ComandoClienteTestDataBuilder conTelefono(Long telefono){
        this.telefono = telefono;
        return this;
    }

    public ComandoClienteTestDataBuilder conCiudad(String ciudad){
        this.ciudad = ciudad;
        return this;
    }

    public ComandoCliente build(){
        return new ComandoCliente(cedula,nombre,direccion,telefono,ciudad);
    }

}
