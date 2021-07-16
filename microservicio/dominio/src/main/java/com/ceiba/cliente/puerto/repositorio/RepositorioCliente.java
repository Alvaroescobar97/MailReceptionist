package com.ceiba.cliente.puerto.repositorio;

import com.ceiba.cliente.modelo.entidad.Cliente;

public interface RepositorioCliente {
    /**
     * Permite crear un cliente
     * @param cliente
     * @return la cedula ingresada que sirve como llave foranea unica
     */
    String crear(Cliente cliente);

    /**
     * Permite validar si existe un usuario con una cedula
     * @param cedula
     * @return si existe o no
     */
    boolean existePorCedula(String cedula);
}
