package com.exemplo.produtos;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Teste automatizado que repete o roteiro da Atividade 7 (o mesmo do Postman).
 * MockMvc simula requisições HTTP sem precisar abrir o Postman.
 * Rodar com: mvn test   (ou o botão "Run Test" do VS Code)
 */
@SpringBootTest
@AutoConfigureMockMvc
class ProdutoControllerTest {

    @Autowired
    private MockMvc mvc;

    @Test
    void roteiroCompletoDaAtividade7() throws Exception {
        String[][] produtos = {
                {"Notebook Dell", "3500.00"},
                {"Mouse Logitech", "120.00"},
                {"Teclado Mecânico", "250.00"},
                {"Monitor LG 24 polegadas", "899.90"},
                {"Headset HyperX", "399.00"}
        };

        // 1) POST de 5 produtos -> 201 Created, IDs 1 a 5
        for (int i = 0; i < produtos.length; i++) {
            String json = "{\"nome\":\"" + produtos[i][0] + "\",\"preco\":" + produtos[i][1] + "}";
            mvc.perform(post("/produtos").contentType(MediaType.APPLICATION_JSON).content(json))
                    .andExpect(status().isCreated())
                    .andExpect(jsonPath("$.id").value(i + 1));
        }

        // 2) GET lista -> 5 itens
        mvc.perform(get("/produtos"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(5)));

        // 3) GET por ID
        mvc.perform(get("/produtos/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nome").value("Notebook Dell"));

        // 4) PUT nos IDs 1, 3 e 5
        mvc.perform(put("/produtos/1").contentType(MediaType.APPLICATION_JSON)
                        .content("{\"nome\":\"Notebook Dell Inspiron\",\"preco\":3799.90}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.preco").value(3799.90));
        mvc.perform(put("/produtos/3").contentType(MediaType.APPLICATION_JSON)
                        .content("{\"nome\":\"Teclado Mecânico RGB\",\"preco\":289.90}"))
                .andExpect(status().isOk());
        mvc.perform(put("/produtos/5").contentType(MediaType.APPLICATION_JSON)
                        .content("{\"nome\":\"Headset HyperX Cloud II\",\"preco\":449.90}"))
                .andExpect(status().isOk());

        // 5) DELETE do ID 1 -> 204, e depois buscar ele dá 404
        mvc.perform(delete("/produtos/1")).andExpect(status().isNoContent());
        mvc.perform(get("/produtos/1")).andExpect(status().isNotFound());

        // 6) GET final -> 4 itens, e o ID 3 está atualizado
        mvc.perform(get("/produtos"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(4)))
                .andExpect(jsonPath("$[1].nome").value("Teclado Mecânico RGB"));
    }
}
