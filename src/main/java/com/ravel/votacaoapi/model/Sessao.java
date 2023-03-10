package com.ravel.votacaoapi.model;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Date;

@Entity
@Getter
@Setter
@Table( name = "sessao" )
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class Sessao {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;
    private LocalDate fimSessao;
    private LocalDate inicioSessao;
    @ManyToOne
    @JoinColumn(name = "id_pauta")
    Pauta pautaId;
}
