# forum

API com Kotlin e Spring Boot de um forum de da Alura.

[collection_forum.zip](https://github.com/vitor0321/forum/files/11952893/collection_forum.zip)

Aprendizados:

* CRUD - GET, POST, PUT e DELETE
* DTOs para representar as informações de input/output da API
* Mappers
* Validação com Bean Validation
* Spring Data JPA para a camada de persistência da API
* Flyway como ferramenta de Migrations
* Paginação
* Interaface
* EntityManager

Rodar a aplicação via Docker usando Container:

* ```docker run -p 3080:8080 forum```


* No Postman pode fazer um Post para:
Pegar Token: ```POST http://localhost:3080/login``` 
passando no body:
```Json
{
    "username":"ana@email.com",
    "password":"123456"
}
```
com isso podemos pegar o token para fazer as outras requisições:

* Listar Topicos: ```GET http://localhost:3080/topicos ```
passando no Authorization o Bearer Token

* Listar Topicos Pager ```GET http://localhost:3080/topicos?page=1&size=5```
* Listar Topicos Pager Order: ```GET http://localhost:8080/topicos?sort=id,desc&sort=titulo&size=3```
* Listar Topicos por curso:  ```GET http://localhost:8080/topicos?nomeCurso=Kotlin```
* Buscar por ID: ```GET http://localhost:8080/topicos/1```
* Buscar relatório de Topicos: ```GET http://localhost:8080/topicos/relatorio```
* Cadastrar Topico: ```POST http://localhost:8080/topicos```

Exemplo de Body:
```json
{
    "titulo":"titulo",
    "mensagem":"mensagem",
    "idCurso":1,
    "idAutor":1
}
```
* Atualizar Topico: ```PUT http://localhost:8080/topicos```

Exemplo de Body:
```json
{
    "id":2,
    "titulo":"titulo atualizar",
    "mensagem":"mensagem atualizada"
}
```

* Delete Topico: ```DELETE  http://localhost:8080/topicos/1```
