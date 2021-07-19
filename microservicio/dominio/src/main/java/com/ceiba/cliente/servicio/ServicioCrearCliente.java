package com.ceiba.cliente.servicio;

import com.ceiba.cliente.modelo.entidad.Cliente;
import com.ceiba.cliente.puerto.repositorio.RepositorioCliente;
import com.ceiba.dominio.ValidadorArgumento;
import com.ceiba.dominio.excepcion.ExcepcionDuplicidad;

public class ServicioCrearCliente {

    private static final String EL_CLIENTE_YA_EXISTE_EN_EL_SISTEMA = "La cedula del cliente ya existe en el sistema";
    private static final String CEDULA_OBLIGATORIA = "La CEDULA es un campo obligatorio";
    private static final String NOMBRE_OBLIGATORIO = "El NOMBRE es un campo obligatorio";
    private static final String DIRECCION_OBLIGATORIA = "La DIRECCION es un campo obligatorio";
    private static final String TELEFONO_OBLIGATORIO = "El TELEFONO es un campo obligatorio";
    private static final String CIUDAD_OBLIGATORIA = "La CIUDAD es un campo obligatorio";

    private final RepositorioCliente repositorioCliente;

    public ServicioCrearCliente(RepositorioCliente repositorioCliente) {
        this.repositorioCliente = repositorioCliente;
    }

    public String ejecutar(Cliente cliente){
        ValidadorArgumento.validarObligatorio(cliente.getCedula(),CEDULA_OBLIGATORIA);
        ValidadorArgumento.validarObligatorio(cliente.getNombre(),NOMBRE_OBLIGATORIO);
        ValidadorArgumento.validarObligatorio(cliente.getDireccion(),DIRECCION_OBLIGATORIA);
        ValidadorArgumento.validarObligatorio(cliente.getTelefono(),TELEFONO_OBLIGATORIO);
        ValidadorArgumento.validarObligatorio(cliente.getCiudad(),CIUDAD_OBLIGATORIA);
        validarExitenciaPrevia(cliente);

        return this.repositorioCliente.crear(cliente);
    }

    public void validarExitenciaPrevia(Cliente cliente) {
        boolean existe = this.repositorioCliente.existePorCedula(cliente.getCedula());
        if(existe){
            throw new ExcepcionDuplicidad(EL_CLIENTE_YA_EXISTE_EN_EL_SISTEMA);
        }
    }

}
