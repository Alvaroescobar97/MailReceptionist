package com.ceiba.dominio.excepcion;

public class ExcepcionPesoInvalido extends RuntimeException{
    private static final long serialVersionUID = 1L;

    public ExcepcionPesoInvalido(String message) {
        super(message);
    }
}
