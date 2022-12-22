package com.ravel.votacaoapi.model;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class Pauta {
    private String id;
    private String titulo;

    public Pauta (String titulo){
        this.titulo = titulo;
    }
}
