package com.ravel.votacaoapi.service;

import com.ravel.votacaoapi.connector.CpfConnectorInterface;
import com.ravel.votacaoapi.connector.StatusCpf;
import com.ravel.votacaoapi.dto.PautaDto;
import com.ravel.votacaoapi.dto.SessaoDto;
import com.ravel.votacaoapi.dto.VotoDto;
import com.ravel.votacaoapi.exception.AssociadoVotouException;
import com.ravel.votacaoapi.exception.PautaInexistenteException;
import com.ravel.votacaoapi.exception.SessaoAbertaException;
import com.ravel.votacaoapi.model.Pauta;
import com.ravel.votacaoapi.model.Sessao;
import com.ravel.votacaoapi.model.Voto;
import com.ravel.votacaoapi.repository.PautaRepository;
import com.ravel.votacaoapi.repository.SessaoRepository;
import com.ravel.votacaoapi.repository.VotoRepository;

import org.assertj.core.api.Assert;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.server.ResponseStatusException;

import utils.PautaProvider;
import utils.VotoProvider;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.atLeastOnce;
import static org.springframework.test.web.client.ExpectedCount.once;

@ExtendWith(MockitoExtension.class)
public class CpfServiceTest {

    @Mock
    private CpfConnectorInterface connector;

    @InjectMocks
    private CpfService service;

    @Test
    void deveLancarExceptionQuandoCpfFormatoInvalido(){
        Exception exception = assertThrows(ResponseStatusException.class, () -> {
            service.verificaCpf("123");
        });
        
        String expectedMessage = "Cpf informado está com formato incorreto";
        String actualMessage = exception.getMessage();

        verify(connector, never()).verificaCpfCooperado("123");
        Assertions.assertThat(actualMessage.contains(expectedMessage));
    }

    @Test
    void deveVerificarCpfEBuscarNoConector(){
        when(connector.verificaCpfCooperado("04953194063")).thenReturn(new StatusCpf("UNABLE_TO_VOTE"));
        StatusCpf resultado = service.verificaCpf("04953194063");
        verify(connector, times(1)).verificaCpfCooperado("04953194063");
        assertThat(resultado.getStatus()).isEqualTo("UNABLE_TO_VOTE");
    }
}
