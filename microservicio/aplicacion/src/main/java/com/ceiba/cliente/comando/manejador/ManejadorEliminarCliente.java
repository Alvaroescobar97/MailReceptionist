package com.ceiba.cliente.comando.manejador;

import com.ceiba.ComandoRespuesta;
import com.ceiba.cliente.servicio.ServicioEliminarCliente;
import com.ceiba.manejador.ManejadorComandoRespuesta;
import org.springframework.stereotype.Component;

@Component
public class ManejadorEliminarCliente implements ManejadorComandoRespuesta<String, ComandoRespuesta<Boolean>> {

    private final ServicioEliminarCliente servicioEliminarCliente;

    public ManejadorEliminarCliente(ServicioEliminarCliente servicioEliminarCliente) {
        this.servicioEliminarCliente = servicioEliminarCliente;
    }

    @Override
    public ComandoRespuesta<Boolean> ejecutar(String cedula) {
        return new ComandoRespuesta<>(this.servicioEliminarCliente.ejecutar(cedula));
    }
}
