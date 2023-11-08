package com.ravel.votacaoapi.controller;

import com.ravel.votacaoapi.dto.SessaoDto;
import com.ravel.votacaoapi.exception.PautaInexistenteException;
import com.ravel.votacaoapi.exception.SessaoAbertaException;
import com.ravel.votacaoapi.service.VotacaoService;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("v1/sessao")
public class SessaoController {

    VotacaoService service;

    @PostMapping(consumes = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity abrirSessao(@RequestBody SessaoDto sessaoDto) {
        try {
            service.abrirSessao(sessaoDto);
        }catch (PautaInexistenteException | SessaoAbertaException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
        return ResponseEntity.ok().build();
    }

}
