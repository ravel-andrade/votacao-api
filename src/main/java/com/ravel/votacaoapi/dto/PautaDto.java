package com.ravel.votacaoapi.dto;

import com.ravel.votacaoapi.model.Pauta;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PautaDto {
    private Long id;
    private String descricao;

    public PautaDto(String descricao) {
        this.descricao = descricao;
    }

    public static PautaDto builder(Pauta pauta) {
        return new PautaDto(pauta.getId(), pauta.getDescricao());
    }

    public static List<PautaDto> buildLista(List<Pauta> all) {
        List<PautaDto> lista = new ArrayList<>();
        all.forEach(pauta -> lista.add(builder(pauta)));
        return lista;
    }
}
