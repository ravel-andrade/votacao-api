package com.ravel.votacaoapi.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class VotoDto {
    int pautaId;
    boolean voto;
    String cpfAssociado;
}
