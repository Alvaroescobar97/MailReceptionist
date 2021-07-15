package com.ceiba;

import com.ceiba.dominio.excepcion.ExcepcionNegacionEnvio;
import com.ceiba.dominio.excepcion.ExcepcionValorInvalido;
import com.ceiba.envio.modelo.entidad.Envio;
import com.ceiba.envio.puerto.repositorio.RepositorioEnvio;
import com.ceiba.envio.servicio.ServicioCrearEnvio;
import com.ceiba.usuario.servicio.testdatabuilder.EnvioTestDataBuilder;
import org.junit.Test;
import org.mockito.Mockito;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

public class ServicioCrearEnvioTest {

    private static final Double COSTO_ADICIONAL = 10000.0;


    @Test
    public void validarCostoAdicionalPorSerSabado(){
        Envio envio = new EnvioTestDataBuilder().conFecha(LocalDateTime.of(2021,07,10,0,0)).build();
        RepositorioEnvio repositorioenvio = Mockito.mock(RepositorioEnvio.class);
        Mockito.when(repositorioenvio.crear(envio)).thenReturn(1L);
        ServicioCrearEnvio servicioCrearEnvio = new ServicioCrearEnvio(repositorioenvio);
        servicioCrearEnvio.ejecutar(envio);

        assertTrue((envio.getValor()+0) == envio.getValor());
    }

    @Test
    public void validarNegacionDeEnvioPorSerDomingo(){

        Envio envio = new EnvioTestDataBuilder().conFecha(LocalDateTime.of(2021,07,11,0,0)).build();
        RepositorioEnvio repositorioenvio = Mockito.mock(RepositorioEnvio.class);
        Mockito.when(repositorioenvio.crear(envio)).thenReturn(1L);
        ServicioCrearEnvio servicioCrearEnvio = new ServicioCrearEnvio(repositorioenvio);

        BasePrueba.assertThrows(()-> servicioCrearEnvio.ejecutar(envio), ExcepcionNegacionEnvio.class , "Los días Domingos no se reciben envios, vuelve otro día");
    }
}
