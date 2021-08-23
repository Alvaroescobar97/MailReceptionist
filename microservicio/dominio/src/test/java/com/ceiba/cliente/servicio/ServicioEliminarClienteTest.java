package com.ceiba.cliente.servicio;

import com.ceiba.BasePrueba;
import com.ceiba.cliente.modelo.entidad.Cliente;
import com.ceiba.cliente.puerto.repositorio.RepositorioCliente;
import com.ceiba.cliente.servicio.testdatabuilder.ClienteTestDataBuilder;
import com.ceiba.dominio.excepcion.ExcepcionValorInvalido;
import org.junit.Test;
import org.mockito.Mockito;

public class ServicioEliminarClienteTest {

    @Test
    public void validarExistenciaPreviaDelClientePorCedula(){
        RepositorioCliente repositorioCliente = Mockito.mock(RepositorioCliente.class);
        Cliente cliente = new ClienteTestDataBuilder().conCedula("1234567890").conNombre("Raul").conDireccion("Cra 23 #23-32").conTelefono(123456789L).conCiudad("Cali").build();
        Mockito.when(repositorioCliente.existePorCedula("1234567890")).thenReturn(false);
        ServicioEliminarCliente servicioEliminarCliente = new ServicioEliminarCliente(repositorioCliente);

        BasePrueba.assertThrows(()-> servicioEliminarCliente.ejecutar(cliente.getCedula()), ExcepcionValorInvalido.class , "El cliente que desea eliminar NO existe en el sistema");
    }
}
