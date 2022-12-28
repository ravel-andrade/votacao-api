package com.ravel.votacaoapi.service;

import com.ravel.votacaoapi.dto.SessaoDto;
import com.ravel.votacaoapi.exception.PautaInexistenteException;
import com.ravel.votacaoapi.exception.SessaoAbertaException;
import com.ravel.votacaoapi.model.Pauta;
import com.ravel.votacaoapi.model.Sessao;
import com.ravel.votacaoapi.repository.PautaRepository;
import com.ravel.votacaoapi.repository.SessaoRepository;
import com.ravel.votacaoapi.repository.VotoRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class VotacaoServiceTest {

    @Mock
    private PautaRepository mockedPautaRepository;

    @Mock
    private SessaoRepository mockedSessaoRepository;

    @Mock
    private VotoRepository mockedVotoRepository;

    @InjectMocks
    private VotacaoService votacaoService;

    @Test
    void deveAbrirSessaoComSucesso(){
        SessaoDto sessaoDto = new SessaoDto(1L, 2);
        votacaoService.abrirSessao(sessaoDto);
        verify(mockedSessaoRepository, atLeast(1)).buscaSessaoAbertaParaPauta(1L);
        verify(mockedSessaoRepository, atLeast(1)).adicionarSessaoAPauta(any(Timestamp.class), any(Timestamp.class), eq(1L));
    }

    @Test
    void naoDeveAbrirSessaoQuandoExistirSessaoAberta(){
        when(mockedSessaoRepository.buscaSessaoAbertaParaPauta(1L)).thenReturn(new Sessao(1L, LocalDate.now(), LocalDate.now(), new Pauta(1L, "")));
        SessaoDto sessaoDto = new SessaoDto(1L, 2);
        votacaoService.abrirSessao(sessaoDto);
        verify(mockedSessaoRepository, atLeast(1)).buscaSessaoAbertaParaPauta(1L);
        verify(mockedSessaoRepository, never()).adicionarSessaoAPauta(any(Timestamp.class), any(Timestamp.class), eq(1L));
    }

    @Test
    void naoDeveAbrirSessaoQuandoJaExistirSessaoAbertaParaPauta(){
        when(mockedSessaoRepository.buscaSessaoAbertaParaPauta(1L)).thenReturn(new Sessao(1L, LocalDate.now(), LocalDate.now(), new Pauta(1L, "")));
        SessaoDto sessaoDto = new SessaoDto(1L, 2);
        assertThrows(SessaoAbertaException.class, () ->{votacaoService.abrirSessao(sessaoDto);});
        verify(mockedSessaoRepository, atLeast(1)).buscaSessaoAbertaParaPauta(1L);
        verify(mockedSessaoRepository, never()).adicionarSessaoAPauta(any(Timestamp.class), any(Timestamp.class), eq(1L));
    }

    @Test
    void naoDeveAbrirSessaoQuandoNaoExistirPautaParaIdInformado(){
        when(mockedPautaRepository.findById(1L)).thenReturn(Optional.empty());
        SessaoDto sessaoDto = new SessaoDto(1L, 2);
        assertThrows(PautaInexistenteException.class, () ->{votacaoService.abrirSessao(sessaoDto);});
        verify(mockedPautaRepository, atLeastOnce()).findById(1L);
        verify(mockedSessaoRepository, never()).buscaSessaoAbertaParaPauta(1L);
        verify(mockedSessaoRepository, never()).adicionarSessaoAPauta(any(Timestamp.class), any(Timestamp.class), eq(1L));
    }
}
