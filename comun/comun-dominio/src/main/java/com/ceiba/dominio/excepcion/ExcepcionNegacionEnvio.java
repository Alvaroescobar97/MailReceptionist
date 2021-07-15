package com.ceiba.dominio.excepcion;

public class ExcepcionNegacionEnvio extends RuntimeException{

    private static final long serialVersionUID = 1L;

    public ExcepcionNegacionEnvio(String message) {
        super(message);
    }
}
