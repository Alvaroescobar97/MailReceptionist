package com.ceiba.envio.servicio;

import com.ceiba.BasePrueba;
import com.ceiba.cliente.puerto.repositorio.RepositorioCliente;
import com.ceiba.dominio.excepcion.*;
import com.ceiba.envio.modelo.entidad.Envio;
import com.ceiba.envio.puerto.repositorio.RepositorioEnvio;
import com.ceiba.envio.servicio.testdatabuilder.EnvioTestDataBuilder;
import org.junit.Test;
import org.mockito.Mockito;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

public class ServicioCrearEnvioTest {

    @Test
    public void validarExistenciaPreviaDeEnvioPorId(){
        RepositorioCliente repositorioCliente = Mockito.mock(RepositorioCliente.class);
        Mockito.when(repositorioCliente.existePorCedula("1234567890")).thenReturn(true);
        Mockito.when(repositorioCliente.existePorCedula("0987654321")).thenReturn(true);

        Envio envio = new EnvioTestDataBuilder().conCedulaEmisor("1234567890").conCedulaReceptor("0987654321").conFecha(LocalDateTime.of(2021,07,16,0,0)).conTipo(Envio.PAQUETE).conPeso(1.0).build();
        RepositorioEnvio repositorioEnvio = Mockito.mock(RepositorioEnvio.class);
        Mockito.when(repositorioEnvio.existePorId(envio.getId())).thenReturn(true);
        ServicioCrearEnvio servicioCrearEnvio = new ServicioCrearEnvio(repositorioEnvio,repositorioCliente);

        BasePrueba.assertThrows(()-> servicioCrearEnvio.ejecutar(envio), ExcepcionDuplicidad.class , "Ya existe un envio con ese ID por favor intente con otro");
    }

    @Test
    public void validarEntregaUltimoEnvioEn5DiasHabilesRealizada(){
        RepositorioCliente repositorioCliente = Mockito.mock(RepositorioCliente.class);
        Mockito.when(repositorioCliente.existePorCedula("1234567890")).thenReturn(true);
        Mockito.when(repositorioCliente.existePorCedula("0987654321")).thenReturn(true);

        Envio envio = new EnvioTestDataBuilder().conCedulaEmisor("0987654321").conCedulaReceptor("1234567890").conFecha(LocalDateTime.of(2021,7,27,0,0)).conTipo(Envio.PAQUETE).conPeso(1.0).build();
        RepositorioEnvio repositorioEnvio = Mockito.mock(RepositorioEnvio.class);
        Mockito.when(repositorioEnvio.ultimoEnvioClienteEmisor(envio.getCedulaEmisor())).thenReturn(LocalDateTime.of(2021,7,19,0,0));
        ServicioCrearEnvio servicioCrearEnvio = new ServicioCrearEnvio(repositorioEnvio,repositorioCliente);

        assertDoesNotThrow(()-> servicioCrearEnvio.ejecutar(envio) , "Ultimo envio pendiente por ser entregado, debe esperar a que la entrega se realice");
    }

    @Test
    public void validarEntregaUltimoEnvioEn5DiasHabilesPendiente(){
        RepositorioCliente repositorioCliente = Mockito.mock(RepositorioCliente.class);
        Mockito.when(repositorioCliente.existePorCedula("1234567890")).thenReturn(true);
        Mockito.when(repositorioCliente.existePorCedula("0987654321")).thenReturn(true);

        Envio envio = new EnvioTestDataBuilder().conCedulaEmisor("0987654321").conCedulaReceptor("1234567890").conFecha(LocalDateTime.of(2021,7,26,0,0)).conTipo(Envio.PAQUETE).conPeso(1.0).build();
        RepositorioEnvio repositorioEnvio = Mockito.mock(RepositorioEnvio.class);
        Mockito.when(repositorioEnvio.ultimoEnvioClienteEmisor(envio.getCedulaEmisor())).thenReturn(LocalDateTime.of(2021,7,19,0,0));
        ServicioCrearEnvio servicioCrearEnvio = new ServicioCrearEnvio(repositorioEnvio,repositorioCliente);

        BasePrueba.assertThrows(()-> servicioCrearEnvio.ejecutar(envio), ExcepcionUltimoEnvioPendiente.class, "Ultimo envio pendiente por ser entregado, debe esperar a que la entrega se realice");
    }

    @Test
    public void validarExistenciaClienteReceptor(){
        RepositorioCliente repositorioCliente = Mockito.mock(RepositorioCliente.class);
        Mockito.when(repositorioCliente.existePorCedula("1234567890")).thenReturn(true);
        Mockito.when(repositorioCliente.existePorCedula("0987654321")).thenReturn(false);

        Envio envio = new EnvioTestDataBuilder().conCedulaEmisor("1234567890").conCedulaReceptor("0987654321").conFecha(LocalDateTime.of(2021,07,16,0,0)).conTipo(Envio.PAQUETE).conPeso(1.0).build();
        RepositorioEnvio repositorioEnvio = Mockito.mock(RepositorioEnvio.class);
        ServicioCrearEnvio servicioCrearEnvio = new ServicioCrearEnvio(repositorioEnvio,repositorioCliente);

        BasePrueba.assertThrows(()-> servicioCrearEnvio.ejecutar(envio), ExcepcionValorInvalido.class , "El RECEPTOR del correo NO existe en el sistema, debe registrarse primero");
    }

    @Test
    public void validarExistenciaClienteEmisor(){
        RepositorioCliente repositorioCliente = Mockito.mock(RepositorioCliente.class);
        Mockito.when(repositorioCliente.existePorCedula("1234567890")).thenReturn(false);
        Mockito.when(repositorioCliente.existePorCedula("0987654321")).thenReturn(true);

        Envio envio = new EnvioTestDataBuilder().conCedulaEmisor("1234567890").conCedulaReceptor("0987654321").conFecha(LocalDateTime.of(2021,07,16,0,0)).conTipo(Envio.PAQUETE).conPeso(1.0).build();
        RepositorioEnvio repositorioEnvio = Mockito.mock(RepositorioEnvio.class);
        ServicioCrearEnvio servicioCrearEnvio = new ServicioCrearEnvio(repositorioEnvio,repositorioCliente);

        BasePrueba.assertThrows(()-> servicioCrearEnvio.ejecutar(envio), ExcepcionValorInvalido.class , "El EMISOR del correo NO existe en el sistema, debe registrarse primero");
    }

    @Test
    public void validarCostoSinRecargo(){
        RepositorioCliente repositorioCliente = Mockito.mock(RepositorioCliente.class);
        Mockito.when(repositorioCliente.existePorCedula("1234567890")).thenReturn(true);
        Mockito.when(repositorioCliente.existePorCedula("0987654321")).thenReturn(true);

        Envio envio = new EnvioTestDataBuilder().conCedulaEmisor("1234567890").conCedulaReceptor("0987654321").conFecha(LocalDateTime.of(2021,07,16,0,0)).conTipo(Envio.PAQUETE).conPeso(1.0).build();
        RepositorioEnvio repositorioEnvio = Mockito.mock(RepositorioEnvio.class);
        ServicioCrearEnvio servicioCrearEnvio = new ServicioCrearEnvio(repositorioEnvio,repositorioCliente);
        servicioCrearEnvio.ejecutar(envio);

        assertTrue( envio.getValor() == 35000.0);
    }

    @Test
    public void validarCostoAdicionalPorSerSabado(){
        RepositorioCliente repositorioCliente = Mockito.mock(RepositorioCliente.class);
        Mockito.when(repositorioCliente.existePorCedula("1234567890")).thenReturn(true);
        Mockito.when(repositorioCliente.existePorCedula("0987654321")).thenReturn(true);

        Envio envio = new EnvioTestDataBuilder().conCedulaEmisor("1234567890").conCedulaReceptor("0987654321").conFecha(LocalDateTime.of(2021,07,10,0,0)).conTipo(Envio.PAQUETE).conPeso(1.0).build();
        RepositorioEnvio repositorioEnvio = Mockito.mock(RepositorioEnvio.class);
        ServicioCrearEnvio servicioCrearEnvio = new ServicioCrearEnvio(repositorioEnvio,repositorioCliente);
        servicioCrearEnvio.ejecutar(envio);

        assertTrue(envio.getValor() == 46000.0);
    }

    @Test
    public void validarNegacionDeEnvioPorSerDomingo(){
        RepositorioCliente repositorioCliente = Mockito.mock(RepositorioCliente.class);
        Mockito.when(repositorioCliente.existePorCedula("1234567890")).thenReturn(true);
        Mockito.when(repositorioCliente.existePorCedula("0987654321")).thenReturn(true);

        Envio envio = new EnvioTestDataBuilder().conCedulaEmisor("1234567890").conCedulaReceptor("0987654321").conFecha(LocalDateTime.of(2021,07,11,0,0)).conTipo(Envio.CARTA).conPeso(0.0).build();
        RepositorioEnvio repositorioEnvio = Mockito.mock(RepositorioEnvio.class);
        ServicioCrearEnvio servicioCrearEnvio = new ServicioCrearEnvio(repositorioEnvio,repositorioCliente);

        BasePrueba.assertThrows(()-> servicioCrearEnvio.ejecutar(envio), ExcepcionNegacionEnvio.class , "Los d??as Domingos no se reciben envios, por favor vuelva otro d??a");
    }

    @Test
    public void validarTipoDeEnvioCartaOPaquete(){
        RepositorioCliente repositorioCliente = Mockito.mock(RepositorioCliente.class);
        Mockito.when(repositorioCliente.existePorCedula("1234567890")).thenReturn(true);
        Mockito.when(repositorioCliente.existePorCedula("0987654321")).thenReturn(true);

        Envio envio = new EnvioTestDataBuilder().conCedulaEmisor("1234567890").conCedulaReceptor("0987654321").conFecha(LocalDateTime.of(2021,07,9,0,0)).conTipo("SOBRE").conPeso(1.0).build();
        RepositorioEnvio repositorioEnvio = Mockito.mock(RepositorioEnvio.class);
        ServicioCrearEnvio servicioCrearEnvio = new ServicioCrearEnvio(repositorioEnvio,repositorioCliente);

        BasePrueba.assertThrows(()-> servicioCrearEnvio.ejecutar(envio), ExcepcionTipoEnvio.class , "Solo se aceptan envios de CARTAS o PAQUETES");
    }

    @Test
    public void validarTipoDeEnvioCarta(){
        RepositorioCliente repositorioCliente = Mockito.mock(RepositorioCliente.class);
        Mockito.when(repositorioCliente.existePorCedula("1234567890")).thenReturn(true);
        Mockito.when(repositorioCliente.existePorCedula("0987654321")).thenReturn(true);

        Envio envio = new EnvioTestDataBuilder().conCedulaEmisor("1234567890").conCedulaReceptor("0987654321").conFecha(LocalDateTime.of(2021,07,9,0,0)).conTipo(Envio.CARTA).conPeso(0.0).build();
        RepositorioEnvio repositorioEnvio = Mockito.mock(RepositorioEnvio.class);
        ServicioCrearEnvio servicioCrearEnvio = new ServicioCrearEnvio(repositorioEnvio,repositorioCliente);

        assertDoesNotThrow(()-> servicioCrearEnvio.ejecutar(envio) , "Solo se aceptan envios de CARTAS o PAQUETES");
    }

    @Test
    public void validarTipoDeEnvioPaquete(){
        RepositorioCliente repositorioCliente = Mockito.mock(RepositorioCliente.class);
        Mockito.when(repositorioCliente.existePorCedula("1234567890")).thenReturn(true);
        Mockito.when(repositorioCliente.existePorCedula("0987654321")).thenReturn(true);

        Envio envio = new EnvioTestDataBuilder().conCedulaEmisor("1234567890").conCedulaReceptor("0987654321").conFecha(LocalDateTime.of(2021,07,9,0,0)).conTipo(Envio.PAQUETE).conPeso(1.0).build();
        RepositorioEnvio repositorioEnvio = Mockito.mock(RepositorioEnvio.class);
        ServicioCrearEnvio servicioCrearEnvio = new ServicioCrearEnvio(repositorioEnvio,repositorioCliente);

        assertDoesNotThrow(()-> servicioCrearEnvio.ejecutar(envio) , "Solo se aceptan envios de CARTAS o PAQUETES");
    }

    @Test
    public void validarPesoDeTipoEnvioCartaCorrecto(){
        RepositorioCliente repositorioCliente = Mockito.mock(RepositorioCliente.class);
        Mockito.when(repositorioCliente.existePorCedula("1234567890")).thenReturn(true);
        Mockito.when(repositorioCliente.existePorCedula("0987654321")).thenReturn(true);

        Envio envio = new EnvioTestDataBuilder().conCedulaEmisor("1234567890").conCedulaReceptor("0987654321").conFecha(LocalDateTime.of(2021,07,9,0,0)).conTipo(Envio.CARTA).conPeso(0.0).build();
        RepositorioEnvio repositorioEnvio = Mockito.mock(RepositorioEnvio.class);
        ServicioCrearEnvio servicioCrearEnvio = new ServicioCrearEnvio(repositorioEnvio,repositorioCliente);

        assertDoesNotThrow(()-> servicioCrearEnvio.ejecutar(envio) , "Las CARTAS NO deben tener peso, es decir peso = 0");
    }

    @Test
    public void validarPesoDeTipoEnvioCartaErroneo(){
        RepositorioCliente repositorioCliente = Mockito.mock(RepositorioCliente.class);
        Mockito.when(repositorioCliente.existePorCedula("1234567890")).thenReturn(true);
        Mockito.when(repositorioCliente.existePorCedula("0987654321")).thenReturn(true);

        Envio envio = new EnvioTestDataBuilder().conCedulaEmisor("1234567890").conCedulaReceptor("0987654321").conFecha(LocalDateTime.of(2021,07,9,0,0)).conTipo(Envio.CARTA).conPeso(1.0).build();
        RepositorioEnvio repositorioEnvio = Mockito.mock(RepositorioEnvio.class);
        ServicioCrearEnvio servicioCrearEnvio = new ServicioCrearEnvio(repositorioEnvio,repositorioCliente);

        BasePrueba.assertThrows(()-> servicioCrearEnvio.ejecutar(envio), ExcepcionPesoInvalido.class , "Las CARTAS NO deben tener peso, es decir peso = 0");
    }

    @Test
    public void validarPesoDeTipoEnvioPaqueteCorrecto(){
        RepositorioCliente repositorioCliente = Mockito.mock(RepositorioCliente.class);
        Mockito.when(repositorioCliente.existePorCedula("1234567890")).thenReturn(true);
        Mockito.when(repositorioCliente.existePorCedula("0987654321")).thenReturn(true);

        Envio envio = new EnvioTestDataBuilder().conCedulaEmisor("1234567890").conCedulaReceptor("0987654321").conFecha(LocalDateTime.of(2021,07,9,0,0)).conTipo(Envio.PAQUETE).conPeso(12.0).build();
        RepositorioEnvio repositorioEnvio = Mockito.mock(RepositorioEnvio.class);
        ServicioCrearEnvio servicioCrearEnvio = new ServicioCrearEnvio(repositorioEnvio,repositorioCliente);

        assertDoesNotThrow(()-> servicioCrearEnvio.ejecutar(envio) , "Los PAQUETES deben tener peso");
    }

    @Test
    public void validarPesoDeTipoEnvioPaqueteErroneo(){
        RepositorioCliente repositorioCliente = Mockito.mock(RepositorioCliente.class);
        Mockito.when(repositorioCliente.existePorCedula("1234567890")).thenReturn(true);
        Mockito.when(repositorioCliente.existePorCedula("0987654321")).thenReturn(true);

        Envio envio = new EnvioTestDataBuilder().conCedulaEmisor("1234567890").conCedulaReceptor("0987654321").conFecha(LocalDateTime.of(2021,07,9,0,0)).conTipo(Envio.PAQUETE).conPeso(0.0).build();
        RepositorioEnvio repositorioEnvio = Mockito.mock(RepositorioEnvio.class);
        ServicioCrearEnvio servicioCrearEnvio = new ServicioCrearEnvio(repositorioEnvio,repositorioCliente);

        BasePrueba.assertThrows(()-> servicioCrearEnvio.ejecutar(envio), ExcepcionPesoInvalido.class , "Los PAQUETES deben tener peso");
    }
}
