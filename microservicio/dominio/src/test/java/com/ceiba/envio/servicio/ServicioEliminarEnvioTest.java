package com.ceiba.envio.servicio;

import com.ceiba.BasePrueba;
import com.ceiba.dominio.excepcion.ExcepcionValorInvalido;
import com.ceiba.envio.modelo.entidad.Envio;
import com.ceiba.envio.puerto.repositorio.RepositorioEnvio;
import com.ceiba.envio.servicio.testdatabuilder.EnvioTestDataBuilder;
import org.junit.Test;
import org.mockito.Mockito;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

public class ServicioEliminarEnvioTest {

    @Test
    public void validarExistenciaPreviaDeEnvioPorId(){
        Envio envio = new EnvioTestDataBuilder().conID(1L).conCedulaEmisor("1234567890").conCedulaReceptor("0987654321").conFecha(LocalDateTime.of(2021,07,16,0,0)).conTipo(Envio.PAQUETE).conPeso(1.0).build();
        RepositorioEnvio repositorioEnvio = Mockito.mock(RepositorioEnvio.class);
        Mockito.when(repositorioEnvio.existePorId(envio.getId())).thenReturn(false);
        ServicioEliminarEnvio servicioEliminarEnvio = new ServicioEliminarEnvio(repositorioEnvio);

        BasePrueba.assertThrows(()-> servicioEliminarEnvio.ejecutar(envio.getId()), ExcepcionValorInvalido.class , "El envio que desea eliminar NO existe en el sistema");
    }

    @Test
    public void validarEliminacion(){
        Envio envio = new EnvioTestDataBuilder().conID(1L).conCedulaEmisor("1234567890").conCedulaReceptor("0987654321").conFecha(LocalDateTime.of(2021,07,16,0,0)).conTipo(Envio.PAQUETE).conPeso(1.0).build();
        RepositorioEnvio repositorioEnvio = Mockito.mock(RepositorioEnvio.class);
        Mockito.when(repositorioEnvio.existePorId(envio.getId())).thenReturn(true);
        ServicioEliminarEnvio servicioEliminarEnvio = new ServicioEliminarEnvio(repositorioEnvio);

        assertDoesNotThrow(()-> servicioEliminarEnvio.ejecutar(envio.getId()) , "El envio que desea eliminar NO existe en el sistema");
    }
}
