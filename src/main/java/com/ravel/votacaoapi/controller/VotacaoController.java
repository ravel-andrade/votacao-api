package com.ravel.votacaoapi.controller;

import com.ravel.votacaoapi.dto.PautaDto;
import com.ravel.votacaoapi.dto.SessaoDto;
import com.ravel.votacaoapi.dto.VotoDto;
import com.ravel.votacaoapi.exception.AssociadoVotouException;
import com.ravel.votacaoapi.exception.PautaInexistenteException;
import com.ravel.votacaoapi.exception.SessaoAbertaException;
import com.ravel.votacaoapi.exception.SessaoInexistenteException;
import com.ravel.votacaoapi.model.Pauta;
import com.ravel.votacaoapi.service.VotacaoService;
import lombok.AllArgsConstructor;
import org.springframework.data.repository.query.Param;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@AllArgsConstructor
public class VotacaoController {

    VotacaoService service;

    @PostMapping(value = "/cadastrar-pauta", consumes = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Void> cadastrarPauta(@RequestBody PautaDto pautaDto) {
        if(pautaDto.getDescricao() == null){
            return ResponseEntity.badRequest().build();
        }
        Pauta pauta = new Pauta(pautaDto.getDescricao());
        service.cadastrarPauta(pauta);
        return ResponseEntity.ok().build();
    }

    @PostMapping(value = "/abrir-sessao", consumes = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity abrirSessao(@RequestBody SessaoDto sessaoDto) {
        if(sessaoDto.getPautaId() == null){
            return ResponseEntity.badRequest().build();
        }
        try {
            service.abrirSessao(sessaoDto);
        }catch (PautaInexistenteException | SessaoAbertaException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
        return ResponseEntity.ok().build();
    }

    @PostMapping(value = "/votar", consumes = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity votar(@RequestBody VotoDto votoDto) {
        try {
            service.cadastrarVoto(votoDto);
        }catch (PautaInexistenteException | SessaoInexistenteException | AssociadoVotouException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
        return ResponseEntity.ok().build();
    }

    @GetMapping(value = "/contabilizar")
    public ResponseEntity contabilizarVotos(@RequestParam("pauta-id") Long pautaId) {
        try {
            return ResponseEntity.ok(new VotoDto(service.contabilizarVotos(pautaId)));
        }catch (PautaInexistenteException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping(value = "/pautas")
    public ResponseEntity<List<PautaDto>> listarPautas(@RequestParam("abertas") boolean abertas) {
        return abertas ? ResponseEntity.ok(service.listarPautasAbertas()) : ResponseEntity.ok(service.listarPautas());
    }
}
