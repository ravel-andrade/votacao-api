package com.ravel.votacaoapi.service;

import com.ravel.votacaoapi.dto.SessaoDto;
import com.ravel.votacaoapi.model.Pauta;
import com.ravel.votacaoapi.repository.PautaRepository;
import com.ravel.votacaoapi.repository.SessaoRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
@RequiredArgsConstructor
public class VotacaoService {
    PautaRepository pautaRepository;
    SessaoRepository sessaoRepository;


    public Pauta cadastrarPauta(Pauta pauta) {
        return pautaRepository.save(pauta);
    }

    public SessaoDto abrirSessao(SessaoDto sessao) {
        return sessao;
    }

    public SessaoDto contabilizarVotos(String titulo) {
        return new SessaoDto();
    }

    public void cadastrarVoto(SessaoDto sessaoDto) {

    }

    public List<Pauta> listarPautas() {
        return pautaRepository.findAll();
    }
}
