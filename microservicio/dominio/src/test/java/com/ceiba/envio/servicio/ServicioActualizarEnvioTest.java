package com.ceiba.envio.servicio;

import com.ceiba.BasePrueba;
import com.ceiba.cliente.puerto.repositorio.RepositorioCliente;
import com.ceiba.dominio.excepcion.ExcepcionDuplicidad;
import com.ceiba.dominio.excepcion.ExcepcionValorInvalido;
import com.ceiba.envio.modelo.entidad.Envio;
import com.ceiba.envio.puerto.repositorio.RepositorioEnvio;
import com.ceiba.envio.servicio.testdatabuilder.EnvioTestDataBuilder;
import org.junit.Test;
import org.mockito.Mockito;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

public class ServicioActualizarEnvioTest {

    @Test
    public void validarExistenciaPreviaDeEnvioPorId(){
        RepositorioCliente repositorioCliente = Mockito.mock(RepositorioCliente.class);
        Mockito.when(repositorioCliente.existePorCedula("1234567890")).thenReturn(true);
        Mockito.when(repositorioCliente.existePorCedula("0987654321")).thenReturn(false);

        Envio envio = new EnvioTestDataBuilder().conCedulaEmisor("1234567890").conCedulaReceptor("0987654321").conFecha(LocalDateTime.of(2021,07,16,0,0)).conTipo(Envio.PAQUETE).conPeso(1.0).build();
        RepositorioEnvio repositorioEnvio = Mockito.mock(RepositorioEnvio.class);
        Mockito.when(repositorioEnvio.crear(envio)).thenReturn(1L);
        ServicioActualizarEnvio servicioActualizarEnvio = new ServicioActualizarEnvio(repositorioEnvio,repositorioCliente);

        BasePrueba.assertThrows(()-> servicioActualizarEnvio.validarExistenciaPrevia(envio), ExcepcionDuplicidad.class , "El envio que desea modificar no existe en el sistema");
    }
}
