package com.ravel.votacaoapi.dto;

import com.ravel.votacaoapi.model.Pauta;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SessaoDto {
    private Long pautaId;
    private Integer duracaoEmMinutos;
}
