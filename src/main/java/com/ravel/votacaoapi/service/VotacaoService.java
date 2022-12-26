package com.ravel.votacaoapi.service;

import com.ravel.votacaoapi.dto.SessaoDto;
import com.ravel.votacaoapi.model.Pauta;
import com.ravel.votacaoapi.repository.PautaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VotacaoService {
    PautaRepository repository;


    public Pauta cadastrarPauta(Pauta pauta) {
        return repository.save(pauta);
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
        return repository.findAll();
    }
}
