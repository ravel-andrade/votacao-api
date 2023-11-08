package com.ravel.votacaoapi.service;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import com.ravel.votacaoapi.connector.CpfConnectorInterface;
import com.ravel.votacaoapi.connector.CpfConnector;
import com.ravel.votacaoapi.connector.StatusCpf;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CpfService {
    CpfConnectorInterface cpfConnector = new CpfConnector();

    public StatusCpf verificaCpf(String cpf) {
        verificaValidadadeCpf(cpf);
        return cpfConnector.verificaCpfCooperado(cpf);
    }

    private void verificaValidadadeCpf(String cpf){
        if (!cpf.matches("[0-9]+") || cpf.length() != 11) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Cpf informado está com formato incorreto");
        }
    }
}
