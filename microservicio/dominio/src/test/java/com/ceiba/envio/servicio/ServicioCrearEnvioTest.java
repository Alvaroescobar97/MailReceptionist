package com.ceiba.envio.servicio;

import com.ceiba.BasePrueba;
import com.ceiba.cliente.puerto.repositorio.RepositorioCliente;
import com.ceiba.dominio.excepcion.*;
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
    public void validarExistenciaClienteReceptor(){
        RepositorioCliente repositorioCliente = Mockito.mock(RepositorioCliente.class);
        Mockito.when(repositorioCliente.existePorCedula("1234567890")).thenReturn(true);
        Mockito.when(repositorioCliente.existePorCedula("0987654321")).thenReturn(false);

        Envio envio = new EnvioTestDataBuilder().conCedulaEmisor("1234567890").conCedulaReceptor("0987654321").conFecha(LocalDateTime.of(2021,07,16,0,0)).conTipo(Envio.PAQUETE).conPeso(1.0).build();
        RepositorioEnvio repositorioenvio = Mockito.mock(RepositorioEnvio.class);
        Mockito.when(repositorioenvio.crear(envio)).thenReturn(1L);
        ServicioCrearEnvio servicioCrearEnvio = new ServicioCrearEnvio(repositorioenvio,repositorioCliente);

        BasePrueba.assertThrows(()-> servicioCrearEnvio.validarExistenciaReceptorEnvio(envio), ExcepcionValorInvalido.class , "El RECEPTOR del correo NO existe en el sistema, debe registrarse primero");
    }

    @Test
    public void validarExistenciaClienteEmisor(){
        RepositorioCliente repositorioCliente = Mockito.mock(RepositorioCliente.class);
        Mockito.when(repositorioCliente.existePorCedula("1234567890")).thenReturn(false);
        Mockito.when(repositorioCliente.existePorCedula("0987654321")).thenReturn(true);

        Envio envio = new EnvioTestDataBuilder().conCedulaEmisor("1234567890").conCedulaReceptor("0987654321").conFecha(LocalDateTime.of(2021,07,16,0,0)).conTipo(Envio.PAQUETE).conPeso(1.0).build();
        RepositorioEnvio repositorioenvio = Mockito.mock(RepositorioEnvio.class);
        Mockito.when(repositorioenvio.crear(envio)).thenReturn(1L);
        ServicioCrearEnvio servicioCrearEnvio = new ServicioCrearEnvio(repositorioenvio,repositorioCliente);

        BasePrueba.assertThrows(()-> servicioCrearEnvio.validarExistenciaEmisorEnvio(envio), ExcepcionValorInvalido.class , "El EMISOR del correo NO existe en el sistema, debe registrarse primero");
    }

    @Test
    public void validarCostoSinRecargo(){
        RepositorioCliente repositorioCliente = Mockito.mock(RepositorioCliente.class);
        Mockito.when(repositorioCliente.existePorCedula("1234567890")).thenReturn(true);
        Mockito.when(repositorioCliente.existePorCedula("0987654321")).thenReturn(true);

        Envio envio = new EnvioTestDataBuilder().conCedulaEmisor("1234567890").conCedulaReceptor("0987654321").conFecha(LocalDateTime.of(2021,07,16,0,0)).conTipo(Envio.PAQUETE).conPeso(1.0).build();
        RepositorioEnvio repositorioenvio = Mockito.mock(RepositorioEnvio.class);
        Mockito.when(repositorioenvio.crear(envio)).thenReturn(1L);
        ServicioCrearEnvio servicioCrearEnvio = new ServicioCrearEnvio(repositorioenvio,repositorioCliente);
        servicioCrearEnvio.cobrarCostoAdicionalPorSerSabado(envio);

        assertTrue( envio.getValor()== 35000.0);
    }

    @Test
    public void validarCostoAdicionalPorSerSabado(){
        RepositorioCliente repositorioCliente = Mockito.mock(RepositorioCliente.class);
        Mockito.when(repositorioCliente.existePorCedula("1234567890")).thenReturn(true);
        Mockito.when(repositorioCliente.existePorCedula("0987654321")).thenReturn(true);

        Envio envio = new EnvioTestDataBuilder().conFecha(LocalDateTime.of(2021,07,10,0,0)).conTipo(Envio.PAQUETE).conPeso(1.0).build();
        RepositorioEnvio repositorioenvio = Mockito.mock(RepositorioEnvio.class);
        Mockito.when(repositorioenvio.crear(envio)).thenReturn(1L);
        ServicioCrearEnvio servicioCrearEnvio = new ServicioCrearEnvio(repositorioenvio,repositorioCliente);
        servicioCrearEnvio.cobrarCostoAdicionalPorSerSabado(envio);

        assertTrue(envio.getValor() == 35000.0 + COSTO_ADICIONAL);
    }

    @Test
    public void validarNegacionDeEnvioPorSerDomingo(){

        Envio envio = new EnvioTestDataBuilder().conFecha(LocalDateTime.of(2021,07,11,0,0)).conTipo(Envio.CARTA).conPeso(1.0).build();
        RepositorioEnvio repositorioenvio = Mockito.mock(RepositorioEnvio.class);
        RepositorioCliente repositorioCliente = Mockito.mock(RepositorioCliente.class);
        Mockito.when(repositorioenvio.crear(envio)).thenReturn(1L);
        ServicioCrearEnvio servicioCrearEnvio = new ServicioCrearEnvio(repositorioenvio,repositorioCliente);

        BasePrueba.assertThrows(()-> servicioCrearEnvio.validarNegacionEnvio(envio), ExcepcionNegacionEnvio.class , "Los días Domingos no se reciben envios, por favor vuelva otro día");
    }

    @Test
    public void validarTipoDeEnvioCartaOPaquete(){

        Envio envio = new EnvioTestDataBuilder().conFecha(LocalDateTime.of(2021,07,9,0,0)).conTipo("SOBRE").conPeso(1.0).build();
        RepositorioEnvio repositorioenvio = Mockito.mock(RepositorioEnvio.class);
        RepositorioCliente repositorioCliente = Mockito.mock(RepositorioCliente.class);
        Mockito.when(repositorioenvio.crear(envio)).thenReturn(1L);
        ServicioCrearEnvio servicioCrearEnvio = new ServicioCrearEnvio(repositorioenvio,repositorioCliente);

        BasePrueba.assertThrows(()-> servicioCrearEnvio.validarTipoDeEnvio(envio), ExcepcionTipoEnvio.class , "Solo se aceptan envios de CARTAS o PAQUETES");
    }

    @Test
    public void validarTipoDeEnvioCarta(){

        Envio envio = new EnvioTestDataBuilder().conFecha(LocalDateTime.of(2021,07,9,0,0)).conTipo(Envio.CARTA).conPeso(0.0).build();
        RepositorioEnvio repositorioenvio = Mockito.mock(RepositorioEnvio.class);
        RepositorioCliente repositorioCliente = Mockito.mock(RepositorioCliente.class);
        Mockito.when(repositorioenvio.crear(envio)).thenReturn(1L);
        ServicioCrearEnvio servicioCrearEnvio = new ServicioCrearEnvio(repositorioenvio,repositorioCliente);

        assertDoesNotThrow(()-> servicioCrearEnvio.validarTipoDeEnvio(envio) , "Solo se aceptan envios de CARTAS o PAQUETES");
    }

    @Test
    public void validarTipoDeEnvioPaquete(){

        Envio envio = new EnvioTestDataBuilder().conFecha(LocalDateTime.of(2021,07,9,0,0)).conTipo(Envio.PAQUETE).conPeso(1.0).build();
        RepositorioEnvio repositorioenvio = Mockito.mock(RepositorioEnvio.class);
        RepositorioCliente repositorioCliente = Mockito.mock(RepositorioCliente.class);
        Mockito.when(repositorioenvio.crear(envio)).thenReturn(1L);
        ServicioCrearEnvio servicioCrearEnvio = new ServicioCrearEnvio(repositorioenvio,repositorioCliente);

        assertDoesNotThrow(()-> servicioCrearEnvio.validarTipoDeEnvio(envio) , "Solo se aceptan envios de CARTAS o PAQUETES");
    }

    @Test
    public void validarPesoDeTipoEnvioCartaCorrecto(){

        Envio envio = new EnvioTestDataBuilder().conFecha(LocalDateTime.of(2021,07,9,0,0)).conTipo(Envio.CARTA).conPeso(0.0).build();
        RepositorioEnvio repositorioenvio = Mockito.mock(RepositorioEnvio.class);
        RepositorioCliente repositorioCliente = Mockito.mock(RepositorioCliente.class);
        Mockito.when(repositorioenvio.crear(envio)).thenReturn(1L);
        ServicioCrearEnvio servicioCrearEnvio = new ServicioCrearEnvio(repositorioenvio,repositorioCliente);

        assertDoesNotThrow(()-> servicioCrearEnvio.validarPesoDependiendoDelTipo(envio) , "Las CARTAS NO deben tener peso, es decir peso = 0");
    }

    @Test
    public void validarPesoDeTipoEnvioCartaErroneo(){

        Envio envio = new EnvioTestDataBuilder().conFecha(LocalDateTime.of(2021,07,9,0,0)).conTipo(Envio.CARTA).conPeso(1.0).build();
        RepositorioEnvio repositorioenvio = Mockito.mock(RepositorioEnvio.class);
        RepositorioCliente repositorioCliente = Mockito.mock(RepositorioCliente.class);
        Mockito.when(repositorioenvio.crear(envio)).thenReturn(1L);
        ServicioCrearEnvio servicioCrearEnvio = new ServicioCrearEnvio(repositorioenvio,repositorioCliente);

        BasePrueba.assertThrows(()-> servicioCrearEnvio.validarPesoDependiendoDelTipo(envio), ExcepcionPesoInvalido.class , "Las CARTAS NO deben tener peso, es decir peso = 0");
    }

    @Test
    public void validarPesoDeTipoEnvioPaqueteCorrecto(){

        Envio envio = new EnvioTestDataBuilder().conFecha(LocalDateTime.of(2021,07,9,0,0)).conTipo(Envio.PAQUETE).conPeso(12.0).build();
        RepositorioEnvio repositorioenvio = Mockito.mock(RepositorioEnvio.class);
        RepositorioCliente repositorioCliente = Mockito.mock(RepositorioCliente.class);
        Mockito.when(repositorioenvio.crear(envio)).thenReturn(1L);
        ServicioCrearEnvio servicioCrearEnvio = new ServicioCrearEnvio(repositorioenvio,repositorioCliente);

        assertDoesNotThrow(()-> servicioCrearEnvio.validarPesoDependiendoDelTipo(envio) , "Los PAQUETES deben tener peso");
    }

    @Test
    public void validarPesoDeTipoEnvioPaqueteErroneo(){

        Envio envio = new EnvioTestDataBuilder().conFecha(LocalDateTime.of(2021,07,9,0,0)).conTipo(Envio.PAQUETE).conPeso(0.0).build();
        RepositorioEnvio repositorioenvio = Mockito.mock(RepositorioEnvio.class);
        RepositorioCliente repositorioCliente = Mockito.mock(RepositorioCliente.class);
        Mockito.when(repositorioenvio.crear(envio)).thenReturn(1L);
        ServicioCrearEnvio servicioCrearEnvio = new ServicioCrearEnvio(repositorioenvio,repositorioCliente);

        BasePrueba.assertThrows(()-> servicioCrearEnvio.validarPesoDependiendoDelTipo(envio), ExcepcionPesoInvalido.class , "Los PAQUETES deben tener peso");
    }
}
