package com.ceiba.cliente.controlador;

import com.ceiba.ComandoRespuesta;
import com.ceiba.cliente.comando.ComandoCliente;
import com.ceiba.cliente.comando.manejador.ManejadorActualizarCliente;
import com.ceiba.cliente.comando.manejador.ManejadorCrearCliente;
import com.ceiba.cliente.comando.manejador.ManejadorEliminarCliente;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/clientes")
@Api(tags = { "Controlador comando clientes"})
public class ComandoControladorCliente {

    private final ManejadorCrearCliente manejadorCrearCliente;
    private final ManejadorActualizarCliente manejadorActualizarCliente;
    private final ManejadorEliminarCliente manejadorEliminarCliente;

    @Autowired
    public ComandoControladorCliente(ManejadorCrearCliente manejadorCrearCliente, ManejadorActualizarCliente manejadorActualizarCliente, ManejadorEliminarCliente manejadorEliminarCliente) {
        this.manejadorCrearCliente = manejadorCrearCliente;
        this.manejadorActualizarCliente = manejadorActualizarCliente;
        this.manejadorEliminarCliente = manejadorEliminarCliente;
    }

    @PostMapping
    @ApiOperation("Crear cliente")
    public ResponseEntity<ComandoRespuesta<String>> crear(@RequestBody ComandoCliente comandoCliente){
        ComandoRespuesta comandoRespuesta = manejadorCrearCliente.ejecutar(comandoCliente);
        return new ResponseEntity<>(comandoRespuesta, HttpStatus.CREATED);
    }

    @PutMapping(value = "/{cedula}")
    @ApiOperation("Actualizar cliente")
    public void actualizar(@RequestBody ComandoCliente comandoCliente, @PathVariable String cedula){
        this.manejadorActualizarCliente.ejecutar(comandoCliente);
    }

    @DeleteMapping(value = "/{cedula}")
    @ApiOperation("Eliminar cliente")
    public ResponseEntity<ComandoRespuesta<Boolean>> eliminar(@PathVariable String cedula){
        ComandoRespuesta comandoRespuesta = this.manejadorEliminarCliente.ejecutar(cedula);
        return new ResponseEntity<>(comandoRespuesta, HttpStatus.OK);
    }
}
