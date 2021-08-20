package com.ceiba.configuracion;

import com.ceiba.cliente.puerto.repositorio.RepositorioCliente;
import com.ceiba.cliente.servicio.ServicioCrearCliente;
import com.ceiba.envio.puerto.repositorio.RepositorioEnvio;
import com.ceiba.envio.servicio.ServicioActualizarEnvio;
import com.ceiba.envio.servicio.ServicioCrearEnvio;
import com.ceiba.envio.servicio.ServicioEliminarEnvio;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanServicio {

    @Bean
    public ServicioCrearCliente servicioCrearCliente(RepositorioCliente repositorioCliente){
        return new ServicioCrearCliente(repositorioCliente);
    }

    @Bean
    public ServicioCrearEnvio servicioCrearEnvio(RepositorioEnvio repositorioEnvio,RepositorioCliente repositorioCliente){
        return new ServicioCrearEnvio(repositorioEnvio,repositorioCliente);
    }

    @Bean
    public ServicioActualizarEnvio servicioActualizarEnvio(RepositorioEnvio repositorioEnvio,RepositorioCliente repositorioCliente){
        return new ServicioActualizarEnvio(repositorioEnvio,repositorioCliente);
    }

    @Bean
    public ServicioEliminarEnvio servicioEliminarEnvio(RepositorioEnvio repositorioEnvio){
        return new ServicioEliminarEnvio(repositorioEnvio);
    }
}
