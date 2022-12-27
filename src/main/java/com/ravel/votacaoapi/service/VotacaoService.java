package com.ravel.votacaoapi.service;

import com.ravel.votacaoapi.dto.SessaoDto;
import com.ravel.votacaoapi.dto.VotoDto;
import com.ravel.votacaoapi.model.Pauta;
import com.ravel.votacaoapi.model.Sessao;
import com.ravel.votacaoapi.model.Voto;
import com.ravel.votacaoapi.repository.PautaRepository;
import com.ravel.votacaoapi.repository.SessaoRepository;
import com.ravel.votacaoapi.repository.VotoRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
        if(!existeSessaoAberta(sessao.getPautaId())){
            sessaoRepository.adicionarSessaoAPauta(LocalDate.now(),
                            LocalDate.now().plus(sessao.getDuracaoEmMinutos(), ChronoUnit.MINUTES),
                            sessao.getPautaId());
        }
    }

    public Map<String, Long> contabilizarVotos(Long pautaId) {
        List<Voto> votosPorPauta = votoRepository.buscaVotosPorPauta(pautaId);
        Map<String, Long> votos = new HashMap<>();
        votos.put("favoraveis", votosPorPauta.stream().filter(Voto::isVoto).count());
        votos.put("contrarios", votosPorPauta.stream().filter(voto -> !voto.isVoto()).count());
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
        return pautaRepository.listarPautasAbertas(LocalDate.now());
    }

    private boolean existeSessaoAberta(Long pautaId) {
        Sessao sessao = sessaoRepository.buscaSessaoAbertaParaPauta(pautaId);
        return sessao != null;
    }

    private boolean associadoVotouEmPauta(Long pautaId, String cpfAssociado) {
        Voto voto = votoRepository.buscaVotoDoAssociadoPorPauta(pautaId, cpfAssociado);
        return voto != null;
    }
}
