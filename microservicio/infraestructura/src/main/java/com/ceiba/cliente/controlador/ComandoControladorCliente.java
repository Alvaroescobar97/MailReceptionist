package com.ceiba.cliente.controlador;

import com.ceiba.ComandoRespuesta;
import com.ceiba.cliente.comando.ComandoCliente;
import com.ceiba.cliente.comando.manejador.ManejadorCrearCliente;
import com.ceiba.envio.comando.ComandoEnvio;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/clientes")
@Api(tags = { "Controlador comando clientes"})
public class ComandoControladorCliente {
    private final ManejadorCrearCliente manejadorCrearCliente;

    @Autowired
    public ComandoControladorCliente(ManejadorCrearCliente manejadorCrearCliente) {
        this.manejadorCrearCliente = manejadorCrearCliente;
    }

    @PostMapping
    @ApiOperation("Crear cliente")
    public ResponseEntity<ComandoRespuesta<String>> crear(@RequestBody ComandoCliente comandoCliente){
        ComandoRespuesta comandoRespuesta = manejadorCrearCliente.ejecutar(comandoCliente);
        return new ResponseEntity<>(comandoRespuesta, HttpStatus.CREATED);
    }
}
