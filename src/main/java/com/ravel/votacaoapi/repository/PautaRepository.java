package com.ravel.votacaoapi.repository;

import com.ravel.votacaoapi.dto.SessaoDto;
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
public interface PautaRepository extends JpaRepository<Pauta, Long> {

    @Query(value = "SELECT * FROM pauta WHERE id in (SELECT id_pauta FROM sessao WHERE (:#{#data_atual} BETWEEN pauta.sessao.dataInicial AND pauta.sessao.dataEncerramento))")
    List<Pauta> listarPautasAbertas(@Param("data_atual") LocalDate dataAtual);
}
