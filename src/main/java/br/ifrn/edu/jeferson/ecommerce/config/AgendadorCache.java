package br.ifrn.edu.jeferson.ecommerce.config;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.concurrent.TimeUnit;

@Component
public class AgendadorCache {
    @Scheduled(fixedDelay = 30, timeUnit = TimeUnit.SECONDS)
    @CacheEvict("categorias")
    public void limparCacheCategorias() {
        System.out.println("Limpando cache de categorias: " + LocalDateTime.now());
    }

    @Scheduled(fixedDelay = 30, timeUnit = TimeUnit.SECONDS)
    @CacheEvict("pedidos")
    public void limparCachePedidos() {
        System.out.println("Limpando cache de pedidos: " + LocalDateTime.now());
    }
}
