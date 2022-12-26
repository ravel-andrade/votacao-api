package com.ravel.votacaoapi.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Entity
@Getter
@Setter
public class Sessao {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id", nullable = false)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "pauta_id")
    private Pauta pauta;
    private Integer duracaoEmMinutos;
    private Date aberturaDaPauta;
    private boolean voto;
}
