package com.ravel.votacaoapi.repository;

import com.ravel.votacaoapi.model.Pauta;
import com.ravel.votacaoapi.model.Sessao;
import com.ravel.votacaoapi.model.Voto;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;
import utils.SqlProvider;
import utils.VotoProvider;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalUnit;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class SessaoRepositoryTest {
    @Autowired
    SessaoRepository sessaoRepository;

    @Autowired
    PautaRepository pautaRepository;


    @Test
    @SqlGroup({
            @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = SqlProvider.LIMPA_BANCO)
    })
    void deveBuscarSessaoAbertaParaPautaComSucesso(){
        pautaRepository.save(new Pauta("placeholder"));
        Timestamp dataAtual = Timestamp.from(Instant.now());
        Timestamp DataFinal = Timestamp.from(Instant.now().plus(1, ChronoUnit.MINUTES));
        sessaoRepository.adicionarSessaoAPauta(dataAtual, DataFinal, 1L);
        Sessao sessao = sessaoRepository.buscaSessaoAbertaParaPauta(1L);
        assertThat(sessao.getPautaId()).isEqualTo(new Pauta(1L, "placeholder"));
    }
}
