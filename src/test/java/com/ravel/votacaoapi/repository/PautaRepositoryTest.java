package com.ravel.votacaoapi.repository;

import com.ravel.votacaoapi.model.Pauta;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;
import utils.PautaProvider;
import utils.SqlProvider;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class PautaRepositoryTest {
    @Autowired
    PautaRepositoryParaTest repository;

    @Test
    @SqlGroup({
            @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = SqlProvider.INSERIR_PAUTA)
    })
    void deveListarPautasAbertas(){
        List<Pauta> pautasAbertas = repository.listarPautasAbertas();
        assertThat(pautasAbertas).isEqualTo(PautaProvider.buildPautasAbertas());
    }
}
