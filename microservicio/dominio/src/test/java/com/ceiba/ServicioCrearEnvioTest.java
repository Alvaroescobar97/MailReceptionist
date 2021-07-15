package com.ceiba;

import com.ceiba.dominio.excepcion.ExcepcionNegacionEnvio;
import com.ceiba.dominio.excepcion.ExcepcionPesoInvalido;
import com.ceiba.dominio.excepcion.ExcepcionTipoEnvio;
import com.ceiba.envio.modelo.entidad.Envio;
import com.ceiba.envio.puerto.repositorio.RepositorioEnvio;
import com.ceiba.envio.servicio.ServicioCrearEnvio;
import com.ceiba.envio.servicio.testdatabuilder.EnvioTestDataBuilder;
import org.junit.Test;
import org.mockito.Mockito;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

public class ServicioCrearEnvioTest {

    private static final Double COSTO_ADICIONAL = 10000.0;

    @Test
    public void validarCostoSinRecargo(){
        Envio envio = new EnvioTestDataBuilder().conFecha(LocalDateTime.of(2021,07,9,0,0)).conTipo(Envio.PAQUETE).conPeso(1.0).build();
        RepositorioEnvio repositorioenvio = Mockito.mock(RepositorioEnvio.class);
        Mockito.when(repositorioenvio.crear(envio)).thenReturn(1L);
        ServicioCrearEnvio servicioCrearEnvio = new ServicioCrearEnvio(repositorioenvio);
        servicioCrearEnvio.ejecutar(envio);

        assertTrue(envio.getValor() == 35000.0);
    }

    @Test
    public void validarCostoAdicionalPorSerSabado(){
        Envio envio = new EnvioTestDataBuilder().conFecha(LocalDateTime.of(2021,07,10,0,0)).conTipo(Envio.PAQUETE).conPeso(1.0).build();
        RepositorioEnvio repositorioenvio = Mockito.mock(RepositorioEnvio.class);
        Mockito.when(repositorioenvio.crear(envio)).thenReturn(1L);
        ServicioCrearEnvio servicioCrearEnvio = new ServicioCrearEnvio(repositorioenvio);
        servicioCrearEnvio.ejecutar(envio);

        assertTrue(envio.getValor() == 35000.0 + COSTO_ADICIONAL);
    }

    @Test
    public void validarNegacionDeEnvioPorSerDomingo(){

        Envio envio = new EnvioTestDataBuilder().conFecha(LocalDateTime.of(2021,07,11,0,0)).conTipo(Envio.CARTA).conPeso(1.0).build();
        RepositorioEnvio repositorioenvio = Mockito.mock(RepositorioEnvio.class);
        Mockito.when(repositorioenvio.crear(envio)).thenReturn(1L);
        ServicioCrearEnvio servicioCrearEnvio = new ServicioCrearEnvio(repositorioenvio);

        BasePrueba.assertThrows(()-> servicioCrearEnvio.ejecutar(envio), ExcepcionNegacionEnvio.class , "Los días Domingos no se reciben envios, por favor vuelva otro día");
    }

    @Test
    public void validarTipoDeEnvioCartaOPaquete(){

        Envio envio = new EnvioTestDataBuilder().conFecha(LocalDateTime.of(2021,07,9,0,0)).conTipo("SOBRE").conPeso(1.0).build();
        RepositorioEnvio repositorioenvio = Mockito.mock(RepositorioEnvio.class);
        Mockito.when(repositorioenvio.crear(envio)).thenReturn(1L);
        ServicioCrearEnvio servicioCrearEnvio = new ServicioCrearEnvio(repositorioenvio);

        BasePrueba.assertThrows(()-> servicioCrearEnvio.ejecutar(envio), ExcepcionTipoEnvio.class , "Solo se aceptan envios de CARTAS o PAQUETES");
    }

    @Test
    public void validarTipoDeEnvioCarta(){

        Envio envio = new EnvioTestDataBuilder().conFecha(LocalDateTime.of(2021,07,9,0,0)).conTipo(Envio.CARTA).conPeso(0.0).build();
        RepositorioEnvio repositorioenvio = Mockito.mock(RepositorioEnvio.class);
        Mockito.when(repositorioenvio.crear(envio)).thenReturn(1L);
        ServicioCrearEnvio servicioCrearEnvio = new ServicioCrearEnvio(repositorioenvio);

        assertDoesNotThrow(()-> servicioCrearEnvio.ejecutar(envio) , "Solo se aceptan envios de CARTAS o PAQUETES");
    }

    @Test
    public void validarTipoDeEnvioPaquete(){

        Envio envio = new EnvioTestDataBuilder().conFecha(LocalDateTime.of(2021,07,9,0,0)).conTipo(Envio.PAQUETE).conPeso(1.0).build();
        RepositorioEnvio repositorioenvio = Mockito.mock(RepositorioEnvio.class);
        Mockito.when(repositorioenvio.crear(envio)).thenReturn(1L);
        ServicioCrearEnvio servicioCrearEnvio = new ServicioCrearEnvio(repositorioenvio);

        assertDoesNotThrow(()-> servicioCrearEnvio.ejecutar(envio) , "Solo se aceptan envios de CARTAS o PAQUETES");
    }

    @Test
    public void validarPesoDeTipoEnvioCartaCorrecto(){

        Envio envio = new EnvioTestDataBuilder().conFecha(LocalDateTime.of(2021,07,9,0,0)).conTipo(Envio.CARTA).conPeso(0.0).build();
        RepositorioEnvio repositorioenvio = Mockito.mock(RepositorioEnvio.class);
        Mockito.when(repositorioenvio.crear(envio)).thenReturn(1L);
        ServicioCrearEnvio servicioCrearEnvio = new ServicioCrearEnvio(repositorioenvio);

        assertDoesNotThrow(()-> servicioCrearEnvio.ejecutar(envio) , "Los PAQUETES deben tener peso, las CARTAS deberian tener peso 0");
    }

    @Test
    public void validarPesoDeTipoEnvioCartaErroneo(){

        Envio envio = new EnvioTestDataBuilder().conFecha(LocalDateTime.of(2021,07,9,0,0)).conTipo(Envio.CARTA).conPeso(1.0).build();
        RepositorioEnvio repositorioenvio = Mockito.mock(RepositorioEnvio.class);
        Mockito.when(repositorioenvio.crear(envio)).thenReturn(1L);
        ServicioCrearEnvio servicioCrearEnvio = new ServicioCrearEnvio(repositorioenvio);

        BasePrueba.assertThrows(()-> servicioCrearEnvio.ejecutar(envio), ExcepcionPesoInvalido.class , "Los PAQUETES deben tener peso, las CARTAS deberian tener peso 0");
    }

    @Test
    public void validarPesoDeTipoEnvioPaqueteCorrecto(){

        Envio envio = new EnvioTestDataBuilder().conFecha(LocalDateTime.of(2021,07,9,0,0)).conTipo(Envio.PAQUETE).conPeso(12.0).build();
        RepositorioEnvio repositorioenvio = Mockito.mock(RepositorioEnvio.class);
        Mockito.when(repositorioenvio.crear(envio)).thenReturn(1L);
        ServicioCrearEnvio servicioCrearEnvio = new ServicioCrearEnvio(repositorioenvio);

        assertDoesNotThrow(()-> servicioCrearEnvio.ejecutar(envio) , "Los PAQUETES deben tener peso, las CARTAS deberian tener peso 0");
    }

    @Test
    public void validarPesoDeTipoEnvioPaqueteErroneo(){

        Envio envio = new EnvioTestDataBuilder().conFecha(LocalDateTime.of(2021,07,9,0,0)).conTipo(Envio.PAQUETE).conPeso(0.0).build();
        RepositorioEnvio repositorioenvio = Mockito.mock(RepositorioEnvio.class);
        Mockito.when(repositorioenvio.crear(envio)).thenReturn(1L);
        ServicioCrearEnvio servicioCrearEnvio = new ServicioCrearEnvio(repositorioenvio);

        BasePrueba.assertThrows(()-> servicioCrearEnvio.ejecutar(envio), ExcepcionPesoInvalido.class , "Los PAQUETES deben tener peso, las CARTAS deberian tener peso 0");
    }
}
