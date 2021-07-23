package com.ceiba.cliente.modelo.entidad;

import com.ceiba.dominio.ValidadorArgumento;

public class Cliente {

    private static final String CEDULA_OBLIGATORIA = "La CEDULA es un campo obligatorio";
    private static final String NOMBRE_OBLIGATORIO = "El NOMBRE es un campo obligatorio";
    private static final String DIRECCION_OBLIGATORIA = "La DIRECCION es un campo obligatorio";
    private static final String TELEFONO_OBLIGATORIO = "El TELEFONO es un campo obligatorio";
    private static final String CIUDAD_OBLIGATORIA = "La CIUDAD es un campo obligatorio";

    private String cedula;
    private String nombre;
    private String direccion;
    private Long telefono;
    private String ciudad;

    public Cliente(String cedula, String nombre, String direccion, Long telefono, String ciudad) {
        ValidadorArgumento.validarObligatorio(cedula,CEDULA_OBLIGATORIA);
        ValidadorArgumento.validarObligatorio(nombre,NOMBRE_OBLIGATORIO);
        ValidadorArgumento.validarObligatorio(direccion,DIRECCION_OBLIGATORIA);
        ValidadorArgumento.validarObligatorio(telefono,TELEFONO_OBLIGATORIO);
        ValidadorArgumento.validarObligatorio(ciudad,CIUDAD_OBLIGATORIA);
        this.cedula = cedula;
        this.nombre = nombre;
        this.direccion = direccion;
        this.telefono = telefono;
        this.ciudad = ciudad;
    }

    public String getCedula() {
        return cedula;
    }
}
