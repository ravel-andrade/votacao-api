package com.ravel.votacaoapi.model;

import lombok.*;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlAttribute;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table( name = "voto" )
@EqualsAndHashCode
public class Voto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private int id;
    @Column(name = "voto_associado", nullable = false)
    boolean votoAssociado;
    String cpfAssociado;
    @ManyToOne
    @JoinColumn(name = "id_pauta")
    Pauta pautaId;
}
