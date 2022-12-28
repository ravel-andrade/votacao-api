package com.ravel.votacaoapi.exception;

public class AssociadoVotouException extends RuntimeException {
    public AssociadoVotouException(String cpf) {
        super(String.format("O associado de cpf %s já tem um voto cadastrado.", cpf));
    }
}
