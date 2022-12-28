package com.ravel.votacaoapi.service;

import com.ravel.votacaoapi.dto.SessaoDto;
import com.ravel.votacaoapi.dto.VotoDto;
import com.ravel.votacaoapi.exception.PautaInexistenteException;
import com.ravel.votacaoapi.exception.SessaoAbertaException;
import com.ravel.votacaoapi.model.Pauta;
import com.ravel.votacaoapi.model.Sessao;
import com.ravel.votacaoapi.model.Voto;
import com.ravel.votacaoapi.repository.PautaRepository;
import com.ravel.votacaoapi.repository.SessaoRepository;
import com.ravel.votacaoapi.repository.VotoRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@AllArgsConstructor
@RequiredArgsConstructor
public class VotacaoService {
    PautaRepository pautaRepository;
    SessaoRepository sessaoRepository;
    VotoRepository votoRepository;


    public Pauta cadastrarPauta(Pauta pauta) {
        return pautaRepository.save(pauta);
    }

    public void abrirSessao(SessaoDto sessao) {
        if( existePauta(sessao.getPautaId()) && !existeSessaoAberta(sessao.getPautaId())){
            int minutos = sessao.getDuracaoEmMinutos() == null ? 1 : sessao.getDuracaoEmMinutos();
            sessaoRepository.adicionarSessaoAPauta(Timestamp.from(Instant.now()),
                    Timestamp.from(Instant.now().plus(minutos, ChronoUnit.MINUTES)),
                    sessao.getPautaId());
        }
    }

    public Map<String, Long> contabilizarVotos(int pautaId) {
        List<Voto> votosPorPauta = votoRepository.buscaVotosPorPauta(pautaId);
        Map<String, Long> votos = new HashMap<>();
        votos.put("favoraveis", votosPorPauta.stream().filter(Voto::isVotoAssociado).count());
        votos.put("contrarios", votosPorPauta.stream().filter(voto -> !voto.isVotoAssociado()).count());
        return votos;
    }

    public void cadastrarVoto(VotoDto votoDto) {
        if(!associadoVotouEmPauta(votoDto.getPautaId(), votoDto.getCpfAssociado())){
            votoRepository.cadastraVoto(votoDto.getPautaId(), votoDto.isVoto(), votoDto.getCpfAssociado());
        }
    }

    public List<Pauta> listarPautas() {
        return pautaRepository.findAll();
    }

    public List<Pauta> listarPautasAbertas() {
        return pautaRepository.listarPautasAbertas();
    }

    private boolean associadoVotouEmPauta(int pautaId, String cpfAssociado) {
        Voto voto = votoRepository.buscaVotoDoAssociadoPorPauta(pautaId, cpfAssociado);
        return voto != null;
    }

    private boolean existePauta(Long pautaId) {
        Optional<Pauta> pauta = pautaRepository.findById(pautaId);
        if(pauta.isEmpty()){
            throw new PautaInexistenteException(pautaId);
        }
        return true;
    }

    private boolean existeSessaoAberta(Long pautaId) {
        Sessao sessao = sessaoRepository.buscaSessaoAbertaParaPauta(pautaId);
        if(sessao != null) {
            throw new SessaoAbertaException(pautaId);
        }
        return false;
    }
}
