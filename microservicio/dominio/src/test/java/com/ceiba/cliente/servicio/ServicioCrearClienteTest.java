package com.ceiba.cliente.servicio;

import com.ceiba.BasePrueba;
import com.ceiba.cliente.modelo.entidad.Cliente;
import com.ceiba.cliente.puerto.repositorio.RepositorioCliente;
import com.ceiba.cliente.servicio.testdatabuilder.ClienteTestDataBuilder;
import com.ceiba.dominio.excepcion.ExcepcionDuplicidad;
import org.junit.Test;
import org.mockito.Mockito;

public class ServicioCrearClienteTest {

    @Test
    public void validarExistenciaPreviaDelClientePorCedula(){
        RepositorioCliente repositorioCliente = Mockito.mock(RepositorioCliente.class);
        Mockito.when(repositorioCliente.existePorCedula("1234567890")).thenReturn(true);
        Cliente cliente = new ClienteTestDataBuilder().conCedula("1234567890").conNombre("Raul").conDireccion("Cra 23 #23-32").conTelefono(123456789L).conCiudad("Cali").build();
        Mockito.when(repositorioCliente.crear(cliente)).thenReturn("1234567890");
        ServicioCrearCliente servicioCrearCliente = new ServicioCrearCliente(repositorioCliente);

        BasePrueba.assertThrows(()-> servicioCrearCliente.ejecutar(cliente), ExcepcionDuplicidad.class , "La cedula del cliente ya existe en el sistema");
    }
}
