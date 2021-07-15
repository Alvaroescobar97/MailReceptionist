package com.ceiba.configuracion;

import com.ceiba.envio.puerto.repositorio.RepositorioEnvio;
import com.ceiba.envio.servicio.ServicioActualizarEnvio;
import com.ceiba.envio.servicio.ServicioCrearEnvio;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanServicio {

    @Bean
    public ServicioCrearEnvio servicioCrearEnvio(RepositorioEnvio repositorioEnvio){
        return new ServicioCrearEnvio(repositorioEnvio);
    }

    @Bean
    public ServicioActualizarEnvio servicioActualizarEnvio(RepositorioEnvio repositorioEnvio){
        return new ServicioActualizarEnvio(repositorioEnvio);
    }
}
