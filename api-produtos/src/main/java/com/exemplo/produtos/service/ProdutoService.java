package com.exemplo.produtos.service;

import com.exemplo.produtos.model.Produto;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

/**
 * SERVICE (camada de regra de negócio).
 *
 * @Service avisa o Spring para criar UMA instância desta classe (um "bean")
 * e guardá-la. Depois o Spring entrega essa instância ao Controller
 * (injeção de dependência).
 *
 * Aqui fica a lógica do CRUD. Os dados ficam numa lista em memória RAM,
 * então TUDO se perde quando a aplicação é reiniciada.
 */
@Service
public class ProdutoService {

    // Nosso "banco de dados" em memória
    private final List<Produto> produtos = new ArrayList<>();

    // Gera IDs 1, 2, 3... AtomicLong é seguro mesmo com várias requisições ao mesmo tempo
    // (lembra da aula de concorrência? O Tomcat atende cada requisição numa thread diferente).
    private final AtomicLong contadorId = new AtomicLong(1);

    /** CREATE: dá um ID ao produto e o guarda na lista. */
    public Produto criar(Produto produto) {
        produto.setId(contadorId.getAndIncrement());
        produtos.add(produto);
        return produto;
    }

    /** READ (todos): devolve uma CÓPIA da lista, para ninguém alterar a original por fora. */
    public List<Produto> listarTodos() {
        return new ArrayList<>(produtos);
    }

    /**
     * READ (por ID).
     * Optional = "caixa" que pode ter um Produto ou estar vazia.
     * Evita retornar null e obriga quem chama a tratar o caso "não encontrado".
     */
    public Optional<Produto> buscarPorId(Long id) {
        return produtos.stream()
                .filter(p -> p.getId().equals(id))
                .findFirst();
    }

    /** UPDATE: se achar o produto, troca nome e preço (o ID continua o mesmo). */
    public Optional<Produto> atualizar(Long id, Produto dadosNovos) {
        return buscarPorId(id).map(existente -> {
            existente.setNome(dadosNovos.getNome());
            existente.setPreco(dadosNovos.getPreco());
            return existente;
        });
    }

    /** DELETE: remove o produto. Retorna true se removeu, false se não existia. */
    public boolean deletar(Long id) {
        return produtos.removeIf(p -> p.getId().equals(id));
    }
}
