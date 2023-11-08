package com.ravel.votacaoapi.controller;

import com.ravel.votacaoapi.dto.PautaDto;
import com.ravel.votacaoapi.service.VotacaoService;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("v1/pauta")
public class PautaController {

    VotacaoService service;
    @PostMapping(consumes = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Void> cadastrarPauta(@RequestBody PautaDto pautaDto) {
        service.cadastrarPauta(pautaDto);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<List<PautaDto>> listarPautas(@RequestParam(value = "abertas", required=false) boolean abertas) {
        return abertas ? ResponseEntity.ok(service.listarPautasAbertas()) : ResponseEntity.ok(service.listarPautas());
    }
}
