# votacao-api

Este sistema tem como objetivo criar e gerenciar a votação de usuários associados.

_*Dependências*_
- Java 17

_*Para rodar a aplicação*_
1. rodar "./gradlew clean build"
2. rodar java "java -jar build/libs/votacao-api-0.0.1-SNAPSHOT.jar"

_Técnologias utilizadas_
- Java 17
- Gradle
- h2
- mySql
- flyway
- SpringBoot
- lombok

# Endpoints disponiveis

Pauta:

Para cadastrar uma nova pauta é possivel utilizar o seguinte recurso.
REQUEST POST: v1/pauta
REQUEST BODY:{
    "descricao":"{descricao}"
}

Para listar todas as pautas.
REQUEST GET: v1/pauta

Para listar somente as pautas abertas.
REQUEST GET: v1/pauta?abertas=true

Sessão:

Para cadastrar uma nova sessao é possivel utilizar o seguinte recurso.
REQUEST POST: v1/sessao
REQUEST BODY:{
"pautaId":{id da pauta que sessao será aberta},
"duracaoEmMinutos": {duração em minutos para tempo da sessão (Caso não informado, será 1 min)}
}

Voto:

Para cadastrar uma novo voto, é possivel utilizar o recurso.
REQUEST POST: v1/voto
REQUEST BODY:{
"pautaId":{id da pauta que sessao será aberta},
"voto": {voto do associado -> true ou false},
"cpfAssociado":{cpf do associado votante}
}

Para obter os votos de uma pauta, é possivel utilizar o recurso.
REQUEST GET: v1/voto?pauta-id={pauta id}

