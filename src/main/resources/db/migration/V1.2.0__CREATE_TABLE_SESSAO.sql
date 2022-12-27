CREATE TABLE sessao
(
    id int AUTO_INCREMENT,
    inicio_sessao TIMESTAMP NOT NULL,
    fim_sessao TIMESTAMP NOT NULL,
    id_pauta int,
   FOREIGN KEY (id_pauta) REFERENCES pauta(id),
    PRIMARY KEY (id)
)