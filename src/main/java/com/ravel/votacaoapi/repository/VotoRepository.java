package com.ravel.votacaoapi.repository;

import com.ravel.votacaoapi.model.Pauta;
import com.ravel.votacaoapi.model.Voto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VotoRepository extends JpaRepository<Voto, Long> {
    @Query(value = "SELECT votos FROM pauta WHERE pauta.id = :#{#pauta_id}")
    List<Voto> buscaVotosPorPauta(@Param("pauta_id") Long pautaId);

    @Modifying
    @Query(value = "INSERT INTO voto(voto, cpf_associado, pauta_id) values(:#{#voto}, :#{#cpf_associado}), :#{#pauta_id})")
    void cadastraVoto(@Param("pauta_id") Long pautaId,@Param("voto") boolean voto,@Param("cpf_associado") String cpfAssociado);

    @Query(value = "SELECT voto FROM voto WHERE voto.cpf_associado = :#{#cpf_associado}) and voto.pauta_id = :#{#pauta_id}")
    Voto buscaVotoDoAssociadoPorPauta(@Param("pauta_id") Long pautaId,@Param("cpf_associado") String cpfAssociado);
}
