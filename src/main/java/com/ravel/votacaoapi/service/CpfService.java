package com.ravel.votacaoapi.service;
import lombok.AllArgsConstructor;
import com.ravel.votacaoapi.connector.*;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@AllArgsConstructor
public class CpfService {
    CpfConnectorInterface cpfConnector;

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
