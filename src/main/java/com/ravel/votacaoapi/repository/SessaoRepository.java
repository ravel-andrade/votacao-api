package com.ravel.votacaoapi.repository;

import com.ravel.votacaoapi.model.Pauta;
import com.ravel.votacaoapi.model.Sessao;
import com.ravel.votacaoapi.model.Voto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface SessaoRepository extends JpaRepository<Sessao, Long> {

    @Query(value = "SELECT * FROM sessao WHERE (TIMESTAMP ':#{#data_atual}' BETWEEN sessao.inicio_sessao AND sessao.fim_sessao) and (sessao.id_pauta = :#{#id_pauta})")
    Sessao buscaSessaoAbertaParaPauta(@Param("id_pauta") Long pauta);

    @Modifying
    @Query(value = "INSERT INTO sessao(inicio_sessao, fim_sessao, id_pauta) values (TIMESTAMP ':#{#inicio_sessao}',TIMESTAMP ':#{#fim_sessao}',:#{#id_pauta})")
    void adicionarSessaoAPauta(@Param("inicio_sessao") LocalDate dataInicial, @Param("fim_sessao") LocalDate dataEncerramento,@Param("id_pauta") Long pautaId);

}
