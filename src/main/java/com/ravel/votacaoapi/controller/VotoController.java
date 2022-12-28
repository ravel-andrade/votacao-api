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
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("v1/voto")
public class VotoController {

    VotacaoService service;

    @PostMapping(consumes = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity votar(@RequestBody VotoDto votoDto) {
        try {
            service.cadastrarVoto(votoDto);
        }catch (PautaInexistenteException | SessaoInexistenteException | AssociadoVotouException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity contabilizarVotos(@RequestParam("pauta-id") Long pautaId) {
        try {
            return ResponseEntity.ok(new VotoDto(service.contabilizarVotos(pautaId)));
        }catch (PautaInexistenteException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
