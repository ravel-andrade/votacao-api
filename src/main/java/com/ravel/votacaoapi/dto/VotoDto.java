package com.ravel.votacaoapi.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class VotoDto {
    Long pautaId;
    boolean voto;
    String cpfAssociado;

    String resultado;

    public VotoDto(Long pautaId, boolean voto, String cpfAssociado) {
        this.pautaId = pautaId;
        this.voto = voto;
        this.cpfAssociado = cpfAssociado;
    }

    public VotoDto(String resultado) {
        this.resultado = resultado;
    }
}
