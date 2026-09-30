package com.exemplo.produtos;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * PONTO DE PARTIDA DA APLICAÇÃO.
 *
 * @SpringBootApplication faz três coisas:
 *  1) marca esta classe como configuração do Spring;
 *  2) liga a autoconfiguração (ex.: sobe o Tomcat porque existe o spring-boot-starter-web);
 *  3) faz o "component scan": procura classes com @RestController, @Service etc.
 *     NESTE pacote (com.exemplo.produtos) e nos subpacotes (model, service, controller).
 *
 * Por isso as outras classes PRECISAM ficar em subpacotes deste aqui.
 */
@SpringBootApplication
public class ProdutosApplication {

    public static void main(String[] args) {
        SpringApplication.run(ProdutosApplication.class, args);
    }
}
