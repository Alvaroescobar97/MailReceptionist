package com.ceiba.envio.puerto.repositorio;

import com.ceiba.envio.modelo.entidad.Envio;

import java.time.LocalDateTime;

public interface RepositorioEnvio {
    /**
     * Permite crear un envio
     * @param envio para creación
     * @return el id del envio
     */
    Long crear(Envio envio);

    /**
     * Permite actualizar un envio
     * @param envio a actualizar
     */
    void actualizar(Envio envio);

    /**
     * Elimina un envio por id
     * @param id identificador del envio
     * @return si se elimino o no
     */
    boolean eliminar(Long id);

    /**
     * Permite validar si existe un envio por id
     * @param id identificador del envio
     * @return si existe o no
     */
    boolean existePorId(Long id);

    /**
     * Devuelve el ultimo envio hecho por un cliente emisor del correo
     * @param cedulaEmisor cedula del cliente emisor
     * @return fecha del ultimo envio
     */
    LocalDateTime ultimoEnvioClienteEmisor(String cedulaEmisor);
}
