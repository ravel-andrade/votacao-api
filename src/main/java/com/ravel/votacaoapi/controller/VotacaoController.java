package com.ravel.votacaoapi.controller;

import com.ravel.votacaoapi.dto.PautaDto;
import com.ravel.votacaoapi.dto.SessaoDto;
import com.ravel.votacaoapi.dto.VotoDto;
import com.ravel.votacaoapi.model.Pauta;
import com.ravel.votacaoapi.service.VotacaoService;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@AllArgsConstructor
public class VotacaoController {

    VotacaoService service;

    @PostMapping(value = "/cadastrar-pauta", consumes = {MediaType.APPLICATION_JSON_VALUE})
    public Pauta cadastrarPauta(@RequestBody PautaDto pautaDto) {
        Pauta pauta = new Pauta(pautaDto.getTitulo());
        return service.cadastrarPauta(pauta);
    }

    @PutMapping(value = "/abrir-sessao", consumes = {MediaType.APPLICATION_JSON_VALUE})
    public void abrirSessao(@RequestBody SessaoDto sessaoDto) {
        service.abrirSessao(sessaoDto);
    }

    @PostMapping(value = "/votar", consumes = {MediaType.APPLICATION_JSON_VALUE})
    public void votar(@RequestBody VotoDto votoDto) {
        service.cadastrarVoto(votoDto);
    }

    @GetMapping(value = "/contabilizar")
    public Map<String, Long> contabilizarVotos(@RequestParam Long pautaId) {
        return service.contabilizarVotos(pautaId);
    }

    @GetMapping(value = "/pautas")
    public List<Pauta> listarPautas() {
        return service.listarPautas();
    }

    @GetMapping(value = "/pautas-abertas")
    public List<Pauta> listarPautasAbertas() {
        return service.listarPautas();
    }
}
