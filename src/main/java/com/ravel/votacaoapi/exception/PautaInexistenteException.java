package com.ravel.votacaoapi.exception;

public class PautaInexistenteException extends RuntimeException {
    public PautaInexistenteException(Long idPauta) {
        super(String.format("Nenhuma pauta encontrada para o id %d.", idPauta));
    }
}
