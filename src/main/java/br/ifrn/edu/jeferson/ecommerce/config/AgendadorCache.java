package br.ifrn.edu.jeferson.ecommerce.config;

import lombok.extern.log4j.Log4j2;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.concurrent.TimeUnit;

@Component
@Log4j2
public class AgendadorCache {
    @Scheduled(fixedDelay = 30, timeUnit = TimeUnit.SECONDS)
    @CacheEvict("categorias")
    public void limparCacheCategorias() {
        log.info("Limpando cache de categorias: {}", LocalDateTime.now());
    }

    @Scheduled(fixedDelay = 30, timeUnit = TimeUnit.SECONDS)
    @CacheEvict("pedidos")
    public void limparCachePedidos() {
        log.info("Limpando cache de pedidos: {}", LocalDateTime.now());
    }
}
