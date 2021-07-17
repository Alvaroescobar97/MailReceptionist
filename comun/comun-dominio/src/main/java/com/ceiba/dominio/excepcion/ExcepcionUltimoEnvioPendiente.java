package com.ceiba.dominio.excepcion;

public class ExcepcionUltimoEnvioPendiente extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public ExcepcionUltimoEnvioPendiente(String message) {
        super(message);
    }
}
