package com.ceiba.cliente.modelo.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class DtoCliente {
    private String cedula;
    private String nombre;
    private String direccion;
    private Long telefono;
    private String ciudad;
}
