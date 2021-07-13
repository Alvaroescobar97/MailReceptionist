package com.ceiba.envio.controlador;

import com.ceiba.envio.consulta.ManejadorListarEnvios;
import com.ceiba.envio.modelo.dto.DtoEnvio;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/envios")
@Api(tags={"Controlador consulta envio"})
public class ConsultaControladorEnvio {

    private final ManejadorListarEnvios manejadorListarEnvios;

    public ConsultaControladorEnvio(ManejadorListarEnvios manejadorListarEnvios) {
        this.manejadorListarEnvios = manejadorListarEnvios;
    }

    @GetMapping
    @ApiOperation("Listar Envios")
    public List<DtoEnvio> listar() {
        return this.manejadorListarEnvios.ejecutar();
    }
}
