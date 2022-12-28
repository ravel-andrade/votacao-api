package com.ravel.votacaoapi.repository;

import com.ravel.votacaoapi.model.Pauta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PautaRepositoryParaTest extends JpaRepository<Pauta, Long> {

    @Query(value = "SELECT * FROM pauta WHERE id in (SELECT id_pauta FROM sessao WHERE (TIMESTAMP '2022-12-28 09:16:00.715718727' BETWEEN sessao.inicio_sessao AND sessao.fim_sessao))", nativeQuery = true)
    List<Pauta> listarPautasAbertas();
}