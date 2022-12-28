package com.ravel.votacaoapi.repository;

import com.ravel.votacaoapi.model.Pauta;
import com.ravel.votacaoapi.model.Sessao;
import com.ravel.votacaoapi.model.Voto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.List;

@Repository
public interface SessaoRepository extends JpaRepository<Sessao, Long> {

    @Query(value = "SELECT * FROM sessao WHERE (SELECT CURRENT_TIMESTAMP() BETWEEN sessao.inicio_sessao AND sessao.fim_sessao) and (sessao.id_pauta = :id_pauta)", nativeQuery = true)
    public Sessao buscaSessaoAbertaParaPauta(@Param("id_pauta") Long pauta);

    @Modifying
    @Transactional
    @Query(value = "INSERT INTO sessao(inicio_sessao, fim_sessao, id_pauta) values (:inicio_sessao, :fim_sessao, :id_pauta)", nativeQuery = true)
    public void adicionarSessaoAPauta(@Param("inicio_sessao") Timestamp dataInicial, @Param("fim_sessao") Timestamp dataEncerramento, @Param("id_pauta") Long pautaId);

}
