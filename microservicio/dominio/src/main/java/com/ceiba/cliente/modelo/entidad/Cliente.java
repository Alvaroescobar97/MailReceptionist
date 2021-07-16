package com.ceiba.cliente.modelo.entidad;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Cliente {

    private String cedula;
    private String nombre;
    private String direccion;
    private Long telefono;
    private String ciudad;

    public Cliente(String cedula, String nombre, String direccion, Long telefono, String ciudad) {
        this.cedula = cedula;
        this.nombre = nombre;
        this.direccion = direccion;
        this.telefono = telefono;
        this.ciudad = ciudad;
    }
}
