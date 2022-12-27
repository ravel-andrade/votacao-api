package com.ravel.votacaoapi.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Date;

@Entity
@Getter
@Setter
public class Sessao {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id", nullable = false)
    private Long id;
    private LocalDate fimDaSessao;
    private LocalDate aberturaDaSessao;

    Integer pautaId;
}
