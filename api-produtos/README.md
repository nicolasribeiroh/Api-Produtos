# Atividade 7 – API REST CRUD de Produtos (Spring Boot)

## Pré-requisitos
- **Java JDK 17 ou superior** (no terminal, `java -version` precisa funcionar)
- **VS Code** com as extensões **Extension Pack for Java** e **Spring Boot Extension Pack**
- **Postman** (ou a extensão **REST Client** do VS Code)

## Como rodar
**Opção A – VS Code (mais fácil, não precisa instalar o Maven)**
1. Descompacte o zip e abra a pasta `api-produtos` no VS Code (File → Open Folder).
2. Espere a extensão Java importar o projeto e baixar as dependências. Na primeira vez, isso leva alguns minutos.
3. Abra `ProdutosApplication.java` e clique em **Run**, acima do método `main`.
4. No terminal deve aparecer: `Tomcat started on port 8080 (http)`.

**Opção B – terminal (se tiver o Maven instalado)**
```
mvn spring-boot:run
```

**Opção C – usar o Spring Initializr**
Gere um projeto em https://start.spring.io com Group `com.exemplo`, Artifact `produtos` e dependência **Spring Web**.
Depois copie a pasta `src` deste zip por cima da do projeto gerado e rode `./mvnw spring-boot:run` (no Windows: `mvnw spring-boot:run`).

## Como testar
- **Postman:** File → Import → `Atividade7.postman_collection.json`. Execute as requisições na ordem (1 → 6).
- **REST Client:** abra `requisicoes.http` e clique em "Send Request".
- **Teste automatizado:** `mvn test`, ou o botão ▶ no `ProdutoControllerTest.java`.

⚠️ A lista fica na memória. Se você reiniciar a aplicação, os produtos somem e os IDs voltam a começar em 1.

## Estrutura (MVC)
```
com.exemplo.produtos
 ├── ProdutosApplication.java      -> sobe o servidor
 ├── model/Produto.java            -> os DADOS
 ├── service/ProdutoService.java   -> a LÓGICA (lista em memória + CRUD)
 └── controller/ProdutoController  -> a PORTA DE ENTRADA HTTP (endpoints)
```

## O caminho de uma requisição (para estudar)
Exemplo: `PUT /produtos/3` com `{"nome":"Teclado RGB","preco":289.9}`
1. O **Tomcat** (servidor embutido) recebe a requisição HTTP na porta 8080.
2. O Spring encontra o método com `@PutMapping("/{id}")` dentro de `@RequestMapping("/produtos")`.
3. `@PathVariable` pega o `3` da URL e `@RequestBody` converte o JSON em `Produto`.
4. O Controller chama `produtoService.atualizar(3, produto)`.
5. O Service procura o ID 3 na lista. Se encontrar, altera e devolve `Optional` com o produto; se não, devolve `Optional` vazio.
6. O Controller transforma o resultado em `200 OK` + JSON, ou em `404 Not Found`.

| CRUD   | Verbo  | Endpoint         | Sucesso        | Erro |
|--------|--------|------------------|----------------|------|
| Create | POST   | /produtos        | 201 Created    | –    |
| Read   | GET    | /produtos        | 200 OK         | –    |
| Read   | GET    | /produtos/{id}   | 200 OK         | 404  |
| Update | PUT    | /produtos/{id}   | 200 OK         | 404  |
| Delete | DELETE | /produtos/{id}   | 204 No Content | 404  |

## Desafios para praticar
1. Faça o POST recusar produto com preço negativo ou nome vazio, respondendo `400 Bad Request` (dica: `ResponseEntity.badRequest()`).
2. Crie `GET /produtos/busca?nome=mouse` usando `@RequestParam`.
3. Adicione um campo `quantidade` ao Produto e veja o que muda no JSON.
4. Troque a lista por um banco H2/PostgreSQL com Spring Data JPA (conteúdo da aula 4).
