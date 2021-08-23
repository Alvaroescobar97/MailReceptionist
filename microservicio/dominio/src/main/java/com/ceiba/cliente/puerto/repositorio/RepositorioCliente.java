package com.ceiba.cliente.puerto.repositorio;

import com.ceiba.cliente.modelo.entidad.Cliente;

public interface RepositorioCliente {
    /**
     * Permite crear un cliente
     * @param cliente a registrar
     * @return la cedula ingresada que sirve como llave foranea unica
     */
    String crear(Cliente cliente);

    /**
     * Permite actualizar un cliente
     * @param cliente a actualizar
     */
    void actualizar(Cliente cliente);

    /**
     * Elimina un cliente por cedula
     * @param cedula identificador del cliente
     * @return si se elimino o no
     */
    boolean eliminar(String cedula);

    /**
     * Permite validar si existe un usuario con una cedula
     * @param cedula
     * @return si existe o no
     */
    boolean existePorCedula(String cedula);
}
