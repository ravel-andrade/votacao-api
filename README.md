# votacao-api

Este sistema tem como objetivo criar e gerenciar a votação de usuários associados.

_*Para rodar a aplicação*_
1. Aṕos baixar a aplicação, rode o comando "docker-compose build" (caso tenha algum problema de autorização, utilize o sudo a frente do comando)
2. rode o comando "docker-compose up"

_Técnologias utilizadas_
- Java 17
- Gradle
- Docker
- h2
- mySql
- flyway
- SpringBoot
- lombok

_Endpoints disponiveis_
-

- Cadastro de novas pautas:

    POST /v1/pauta

    requestBody: {"descricao":"{descricaoDesejada}"}
