package com.ravel.votacaoapi.connector;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@ExtendWith(MockitoExtension.class)
public class ConnectorTest {

    private CpfConnectorInterface connector = new CpfConnector();

    @Test
    void deveLancarExceptionQuandoCpfFormatoInvalido(){
        StatusCpf resultado = connector.verificaCpfCooperado("04953194063");
        assertThat(resultado.getStatus()).isEqualTo("UNABLE_TO_VOTE");
    }
}
