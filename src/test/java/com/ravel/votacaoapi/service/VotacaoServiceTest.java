package com.ravel.votacaoapi.service;

import com.ravel.votacaoapi.dto.PautaDto;
import com.ravel.votacaoapi.dto.SessaoDto;
import com.ravel.votacaoapi.dto.VotoDto;
import com.ravel.votacaoapi.exception.AssociadoVotouException;
import com.ravel.votacaoapi.exception.PautaInexistenteException;
import com.ravel.votacaoapi.exception.SessaoAbertaException;
import com.ravel.votacaoapi.model.Pauta;
import com.ravel.votacaoapi.model.Sessao;
import com.ravel.votacaoapi.model.Voto;
import com.ravel.votacaoapi.repository.PautaRepository;
import com.ravel.votacaoapi.repository.SessaoRepository;
import com.ravel.votacaoapi.repository.VotoRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import utils.PautaProvider;
import utils.VotoProvider;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.atLeastOnce;
import static org.springframework.test.web.client.ExpectedCount.once;

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
    void deveCadastrarPautaComSucesso(){
        Pauta pauta = new Pauta("placeholder");
        votacaoService.cadastrarPauta(new PautaDto("placeholder"));
        verify(mockedPautaRepository, atLeast(1)).save(pauta);
    }

    @Test
    void deveAbrirSessaoComSucesso(){
        when(mockedPautaRepository.findById(1L)).thenReturn(Optional.of(new Pauta(1L, "placeholder")));
        SessaoDto sessaoDto = new SessaoDto(1L, 2);
        votacaoService.abrirSessao(sessaoDto);
        verify(mockedSessaoRepository, atLeast(1)).buscaSessaoAbertaParaPauta(1L);
        verify(mockedSessaoRepository, atLeast(1)).adicionarSessaoAPauta(any(Timestamp.class), any(Timestamp.class), eq(1L));
    }

    @Test
    void naoDeveAbrirSessaoQuandoJaExistirSessaoAbertaParaPauta(){
        when(mockedPautaRepository.findById(1L)).thenReturn(Optional.of(new Pauta(1L, "placeholder")));
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

    @Test
    void deveContabilizarVotos(){
        when(mockedPautaRepository.findById(1L)).thenReturn(Optional.of(new Pauta(1L, "placeholder")));
        when(mockedVotoRepository.buscaVotosPorPauta(1L)).thenReturn(VotoProvider.buildVotosEsperados());
        String resultado = votacaoService.contabilizarVotos(1L);
        verify(mockedVotoRepository, atLeastOnce()).buscaVotosPorPauta(1L);
        assertThat(resultado).isEqualTo("aprovado");
    }

    @Test
    void naoDeveContabilizarVotos(){
        when(mockedPautaRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(PautaInexistenteException.class, () ->{votacaoService.contabilizarVotos(1L);});
        verify(mockedVotoRepository, never()).buscaVotosPorPauta(1L);
    }

    @Test
    void deveCadastrarVotoComSucesso(){
        when(mockedPautaRepository.findById(1L)).thenReturn(Optional.of(new Pauta(1L, "placeholder")));
        when(mockedVotoRepository.buscaVotoDoAssociadoPorPauta(1L, "000000000000")).thenReturn(null);
        when(mockedSessaoRepository.buscaSessaoAbertaParaPauta(1L)).thenReturn(new Sessao(1L, LocalDate.now(), LocalDate.now(), new Pauta()));
        votacaoService.cadastrarVoto(new VotoDto(1L, true, "000000000000"));
        verify(mockedVotoRepository, atLeastOnce()).cadastraVoto(1L, true, "000000000000");
    }

    @Test
    void naoDeveCadastrarVotoQuandoPautaNaoExistir(){
        when(mockedPautaRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(PautaInexistenteException.class, () ->{votacaoService.cadastrarVoto(new VotoDto(1L, true, "000000000000"));});
        verify(mockedVotoRepository, never()).cadastraVoto(1L, true, "000000000000");
    }

    @Test
    void naoDeveCadastrarVotoQuandoAssociadoJaVotou(){
        when(mockedPautaRepository.findById(1L)).thenReturn(Optional.of(new Pauta(1L, "placeholder")));
        when(mockedVotoRepository.buscaVotoDoAssociadoPorPauta(1L, "000000000000")).thenReturn(new Voto(1, true, "000000000000", new Pauta()));
        assertThrows(AssociadoVotouException.class, () ->{votacaoService.cadastrarVoto(new VotoDto(1L, true, "000000000000"));});
        verify(mockedVotoRepository, never()).cadastraVoto(1L, true, "000000000000");
    }

    @Test
    void deveListarTodasAsPautas(){
        votacaoService.listarPautas();
        verify(mockedPautaRepository, atLeastOnce()).findAll();
    }

    @Test
    void deveListarTodasAsPautasAbertas(){
        votacaoService.listarPautasAbertas();
        verify(mockedPautaRepository, atLeastOnce()).listarPautasAbertas();
    }
}
