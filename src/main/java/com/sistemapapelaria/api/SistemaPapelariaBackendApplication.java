package com.sistemapapelaria.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.event.EventListener;

@SpringBootApplication
public class SistemaPapelariaBackendApplication extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(SistemaPapelariaBackendApplication.class, args);
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(SistemaPapelariaBackendApplication.class);
    }

    @EventListener(ApplicationReadyEvent.class)
    public void afterInit() {
        System.out.println("Aplicação SistemaPapelariaBackend iniciada com sucesso!");
    }
}
