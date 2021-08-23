package com.ceiba.cliente.servicio;

import com.ceiba.cliente.modelo.entidad.Cliente;
import com.ceiba.cliente.puerto.repositorio.RepositorioCliente;
import com.ceiba.dominio.excepcion.ExcepcionDuplicidad;
import com.ceiba.dominio.excepcion.ExcepcionValorInvalido;

public class ServicioEliminarCliente {

    public static final String EL_CLIENTE_NO_EXISTE = "El cliente que desea eliminar NO existe en el sistema";

    private final RepositorioCliente repositorioCliente;

    public ServicioEliminarCliente(RepositorioCliente repositorioCliente) {
        this.repositorioCliente = repositorioCliente;
    }

    public Boolean ejecutar(String cedula){
        validarExitenciaPrevia(cedula);

        return this.repositorioCliente.eliminar(cedula);
    }

    private void validarExitenciaPrevia(String cedula) {
        boolean existe = this.repositorioCliente.existePorCedula(cedula);
        if(!existe){
            throw new ExcepcionValorInvalido(EL_CLIENTE_NO_EXISTE);
        }
    }
}
