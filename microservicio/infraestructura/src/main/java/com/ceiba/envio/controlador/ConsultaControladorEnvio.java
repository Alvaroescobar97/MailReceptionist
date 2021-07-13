package com.ceiba.envio.controlador;

import com.ceiba.usuario.consulta.ManejadorListarEnvios;
import com.ceiba.usuario.modelo.dto.DtoEnvio;
import com.ceiba.usuario.modelo.dto.DtoUsuario;
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
