package com.ravel.votacaoapi.exception;

public class SessaoAbertaException extends RuntimeException {
    public SessaoAbertaException(Long idPauta) {
        super(String.format("Já existe uma sessao aberta para a pauta %d.", idPauta));
    }
}
