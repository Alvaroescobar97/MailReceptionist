package com.ceiba.envio.puerto.repositorio;

import com.ceiba.envio.modelo.entidad.Envio;

import java.time.LocalDateTime;

public interface RepositorioEnvio {
    /**
     * Permite crear un envio
     * @param envio
     * @return el id del envio
     */
    Long crear(Envio envio);

    void actualizar(Envio envio);

    /**
     * Elimina un envio por id
     * @param id
     * @return si se elimino o no
     */
    boolean eliminar(Long id);

    /**
     * Permite validar si existe un envio por id
     * @param id
     * @return si existe o no
     */
    boolean existePorId(Long id);

    /**
     * Devuelve el ultimo envio hecho por un cliente emisor del correo
     * @param cedulaEmisor
     * @return ultimo envio
     */
    LocalDateTime ultimoEnvioClienteEmisor(String cedulaEmisor);
}
