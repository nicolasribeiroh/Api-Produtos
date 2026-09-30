package com.exemplo.produtos.model;

/**
 * MODEL (o "M" do MVC).
 *
 * Representa os DADOS de um produto. Não tem regra de negócio nem nada de HTTP.
 *
 * O Spring (pela biblioteca Jackson) converte automaticamente:
 *   JSON  -> objeto Produto  (quando chega um POST/PUT)
 *   objeto Produto -> JSON   (quando a API responde)
 * Para isso ele usa o construtor vazio + getters/setters.
 */
public class Produto {

    private Long id;       // gerado pelo Service, o cliente não precisa enviar
    private String nome;
    private double preco;

    // Construtor vazio: OBRIGATÓRIO para o Jackson criar o objeto a partir do JSON
    public Produto() {
    }

    public Produto(Long id, String nome, double preco) {
        this.id = id;
        this.nome = nome;
        this.preco = preco;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public double getPreco() {
        return preco;
    }

    public void setPreco(double preco) {
        this.preco = preco;
    }
}
