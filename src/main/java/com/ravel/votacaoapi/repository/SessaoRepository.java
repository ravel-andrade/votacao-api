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

    @Query(value = "SELECT * FROM sessao WHERE (:#{#data_atual} BETWEEN sessao.dataInicial AND sessao.dataEncerramento) and (sessao.pauta_id = :#{#pauta_id})")
    Sessao buscaSessaoAbertaParaPauta(@Param("pauta_id") Long pauta);

    @Modifying
    @Query(value = "INSERT INTO sessao(abertura_sessao, fim_sessao, pauta_id) values (:#{#abertura_sessao},:#{#fim_sessao},:#{#pauta_id})")
    void adicionarSessaoAPauta(@Param("abertura_sessao") LocalDate dataInicial, @Param("fim_sessao") LocalDate dataEncerramento,@Param("pauta_id") Long pautaId);

}
