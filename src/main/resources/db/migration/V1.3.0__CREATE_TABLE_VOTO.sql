CREATE TABLE voto
(
    id int AUTO_INCREMENT,
    cpf_associado VARCHAR(11) NOT NULL,
    voto_associado BIT NOT NULL,
    id_pauta int,
    FOREIGN KEY (id_pauta) REFERENCES pauta(id),
    PRIMARY KEY (id)
)