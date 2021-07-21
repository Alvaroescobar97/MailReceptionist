package com.ceiba.envio.controlador;

import com.ceiba.ApplicationMock;
import com.ceiba.envio.comando.ComandoEnvio;
import com.ceiba.envio.servicio.testdatabuilder.ComandoEnvioTestDataBuilder;
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

import java.time.LocalDateTime;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes= ApplicationMock.class)
@WebMvcTest(ComandoControladorEnvio.class)
public class ComandoControladorEnvioTest {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    @Test
    public void crear() throws Exception{
        ComandoEnvio envio = new ComandoEnvioTestDataBuilder().conFecha(LocalDateTime.of(2021,7,19,0,0)).build();

        mockMvc.perform(post("/envios")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(envio)))
                .andExpect(status().isCreated())
                .andExpect(content().json("{'valor': 2}"));
    }

    @Test
    public void actualizar() throws Exception{
        Long id = 1L;
        ComandoEnvio envio = new ComandoEnvioTestDataBuilder().conFecha(LocalDateTime.of(2021,6,19,0,0)).build();

        mockMvc.perform(put("/envios/{id}",id)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(envio)))
                .andExpect(status().isOk());
    }
}
