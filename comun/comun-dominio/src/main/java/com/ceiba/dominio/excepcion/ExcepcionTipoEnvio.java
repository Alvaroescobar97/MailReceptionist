package com.ceiba.dominio.excepcion;

public class ExcepcionTipoEnvio extends  RuntimeException{
    private static final long serialVersionUID = 1L;

    public ExcepcionTipoEnvio(String message) {
        super(message);
    }
}
