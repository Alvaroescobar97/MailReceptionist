package com.ceiba.envio.controlador;

import com.ceiba.ComandoRespuesta;
import com.ceiba.envio.comando.ComandoEnvio;
import com.ceiba.envio.comando.manejador.ManejadorActualizarEnvio;
import com.ceiba.envio.comando.manejador.ManejadorCrearEnvio;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/envios")
@Api(tags = { "Controlador comando envios"})
public class ComandoControladorEnvio {

    private final ManejadorCrearEnvio manejadorCrearEnvio;
    private final ManejadorActualizarEnvio manejadorActualizarEnvio;

    @Autowired
    public ComandoControladorEnvio(ManejadorCrearEnvio manejadorCrearEnvio, ManejadorActualizarEnvio manejadorActualizarEnvio) {
        this.manejadorCrearEnvio = manejadorCrearEnvio;
        this.manejadorActualizarEnvio = manejadorActualizarEnvio;
    }

    @PostMapping
    @ApiOperation("Crear Envio")
    public ResponseEntity<ComandoRespuesta<Long>> crear(@RequestBody ComandoEnvio comandoEnvio){
        ComandoRespuesta comandoRespuesta = manejadorCrearEnvio.ejecutar(comandoEnvio);
        return new ResponseEntity<>(comandoRespuesta, HttpStatus.CREATED);
    }

    @PutMapping(value = "/{id}")
    @ApiOperation("Actualizar Envio")
    public ResponseEntity<Long> actualizar(@RequestBody ComandoEnvio comandoEnvio, @PathVariable Long id){
        comandoEnvio.setId(id);
        manejadorActualizarEnvio.ejecutar(comandoEnvio);
        return new ResponseEntity<>(id,HttpStatus.OK);
    }

}
