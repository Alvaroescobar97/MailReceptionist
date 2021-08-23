package com.ceiba.cliente.servicio;

import com.ceiba.cliente.modelo.entidad.Cliente;
import com.ceiba.cliente.puerto.repositorio.RepositorioCliente;
import com.ceiba.dominio.excepcion.ExcepcionValorInvalido;

public class ServicioActualizarCliente {

    public static final String EL_CLIENTE_NO_EXISTE = "El cliente NO existe en el sistema, debe registrarse primero";

    private final RepositorioCliente repositorioCliente;

    public ServicioActualizarCliente(RepositorioCliente repositorioCliente) {
        this.repositorioCliente = repositorioCliente;
    }

    public void ejecutar(Cliente cliente){
        validarExitenciaPrevia(cliente);

        this.repositorioCliente.actualizar(cliente);
    }

    private void validarExitenciaPrevia(Cliente cliente) {
        boolean existe = this.repositorioCliente.existePorCedula(cliente.getCedula());
        if(!existe){
            throw new ExcepcionValorInvalido(EL_CLIENTE_NO_EXISTE);
        }
    }
}
