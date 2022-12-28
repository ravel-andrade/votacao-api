package com.ravel.votacaoapi.exception;

public class SessaoInexistenteException extends RuntimeException {
    public SessaoInexistenteException(Long idPauta) {
        super(String.format("Não existe uma sessao aberta para a pauta %d.", idPauta));
    }
}
