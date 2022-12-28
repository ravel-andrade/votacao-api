package com.ravel.votacaoapi.repository;

import com.ravel.votacaoapi.model.Pauta;
import com.ravel.votacaoapi.model.Voto;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;
import utils.SqlProvider;
import utils.VotoProvider;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class VotoRepositoryTest {
    @Autowired
    VotoRepository votoRepository;

    @Autowired
    PautaRepository pautaRepository;


    @Test
    @SqlGroup({
            @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = SqlProvider.INSERIR_VOTOS)
    })
    void deveBuscaVotosPorPautaComSucesso(){
        pautaRepository.save(new Pauta("placeholder"));
        List<Voto> resultado = votoRepository.buscaVotosPorPauta(1);
        assertThat(resultado).hasSize(5);
        assertThat(resultado).isEqualTo(VotoProvider.buildVotosEsperados());
    }

    @Test
    @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = SqlProvider.LIMPA_BANCO)
    void deveCadastraVotoComSucesso(){
        pautaRepository.save(new Pauta("placeholder"));
        votoRepository.cadastraVoto(1, true, "00000000000");
        Voto votoEsperado = votoRepository.buscaVotoDoAssociadoPorPauta(1, "00000000000");
        assertThat(votoEsperado).isEqualTo(VotoProvider.VOTO);
    }

    @Test
    @SqlGroup({
            @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = SqlProvider.INSERIR_VOTOS)
    })
    void deveBuscarVotoDoAssociadoPorPautaComSucesso(){
        Voto resultado = votoRepository.buscaVotoDoAssociadoPorPauta(1, "00000000000");
        assertThat(resultado).isEqualTo(VotoProvider.VOTO);
    }
}
