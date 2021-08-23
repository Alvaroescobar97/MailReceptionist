package com.ceiba.cliente.controlador;

import com.ceiba.ApplicationMock;
import com.ceiba.cliente.comando.ComandoCliente;
import com.ceiba.cliente.servicio.testdatabuilder.ComandoClienteTestDataBuilder;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes= ApplicationMock.class)
@WebMvcTest(ComandoControladorCliente.class)
public class ComandoControladorClienteTest {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    @Test
    public void crear() throws Exception{
        ComandoCliente cliente = new ComandoClienteTestDataBuilder().conCedula("1478529636").conNombre("Felipe").conDireccion("Calle 23 #23-23").conTelefono(741852963L).conCiudad("Medellin").build();

        mockMvc.perform(post("/clientes")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(cliente)))
                .andExpect(status().isCreated())
                .andExpect(content().json("{'valor': '1478529636'}"));
    }

    @Test
    public void actualizar() throws Exception{
        ComandoCliente cliente = new ComandoClienteTestDataBuilder().conCedula("1234567890").conNombre("Felipe Edit").conDireccion("Calle 98 #98-98").conTelefono(741852963L).conCiudad("Buga").build();

        mockMvc.perform(put("/clientes/{cedula}", cliente.getCedula())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(cliente)))
                .andExpect(status().isOk());
    }

    @Test
    public void eliminar() throws Exception{
        String cedula = "1234567890";

        mockMvc.perform(delete("/clientes/{cedula}", cedula)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(cedula)))
                .andExpect(status().isOk())
                .andExpect(content().json("{'valor': true}"));
    }

}
