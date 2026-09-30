package com.exemplo.produtos.controller;

import com.exemplo.produtos.model.Produto;
import com.exemplo.produtos.service.ProdutoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * CONTROLLER (o "C" do MVC): porta de entrada da API.
 *
 * @RestController   -> as respostas dos métodos viram JSON automaticamente.
 * @RequestMapping   -> todos os endpoints desta classe começam com /produtos.
 *
 * O Controller NÃO tem regra de negócio: ele só recebe a requisição HTTP,
 * chama o Service e devolve a resposta com o status HTTP certo.
 *
 * ResponseEntity<T> = corpo da resposta (T) + status HTTP (200, 201, 204, 404...).
 */
@RestController
@RequestMapping("/produtos")
public class ProdutoController {

    // Injeção de dependência: o Spring coloca aqui a instância do ProdutoService.
    // Nós nunca escrevemos "new ProdutoService()".
    @Autowired
    private ProdutoService produtoService;

    /**
     * POST /produtos
     * @RequestBody: converte o JSON do corpo da requisição em um objeto Produto.
     * Retorna 201 Created (o padrão REST para "recurso criado").
     */
    @PostMapping
    public ResponseEntity<Produto> criar(@RequestBody Produto produto) {
        Produto novo = produtoService.criar(produto);
        return ResponseEntity.status(HttpStatus.CREATED).body(novo);
    }

    /** GET /produtos -> 200 OK com a lista (pode ser vazia: []). */
    @GetMapping
    public ResponseEntity<List<Produto>> listarTodos() {
        return ResponseEntity.ok(produtoService.listarTodos());
    }

    /**
     * GET /produtos/{id}
     * @PathVariable: pega o {id} da URL. Ex.: /produtos/3 -> id = 3.
     * 200 OK se achar, 404 Not Found se não existir.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Produto> buscarPorId(@PathVariable Long id) {
        return produtoService.buscarPorId(id)
                .map(ResponseEntity::ok)                      // achou -> 200 + produto
                .orElse(ResponseEntity.notFound().build());   // não achou -> 404
    }

    /** PUT /produtos/{id} -> 200 OK com o produto atualizado, ou 404. */
    @PutMapping("/{id}")
    public ResponseEntity<Produto> atualizar(@PathVariable Long id,
                                             @RequestBody Produto produto) {
        return produtoService.atualizar(id, produto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /** DELETE /produtos/{id} -> 204 No Content (deu certo, sem corpo), ou 404. */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        if (produtoService.deletar(id)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
