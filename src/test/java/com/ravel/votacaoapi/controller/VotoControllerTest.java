package com.ravel.votacaoapi.controller;

import com.ravel.votacaoapi.connector.StatusCpf;
import com.ravel.votacaoapi.dto.SessaoDto;
import com.ravel.votacaoapi.dto.VotoDto;
import com.ravel.votacaoapi.exception.AssociadoVotouException;
import com.ravel.votacaoapi.exception.PautaInexistenteException;
import com.ravel.votacaoapi.exception.SessaoInexistenteException;
import com.ravel.votacaoapi.service.CpfService;
import com.ravel.votacaoapi.service.VotacaoService;
import lombok.SneakyThrows;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(VotoController.class)
public class VotoControllerTest {

    @Autowired
    private MockMvc mvc;
    @MockBean
    VotacaoService mockedVotacaoService;
    @MockBean
    CpfService mockedCpfService;

    static final String URL = "/v1/voto";

    @Test
    void deveRetornarBadRequestQuandoFormatoCpfInvalido() throws Exception{
        ResponseStatusException exception = new ResponseStatusException(HttpStatus.BAD_REQUEST, "Cpf informado está com formato incorreto");
        doThrow(exception).when(mockedCpfService).verificaCpf("123");
        mvc.perform(get(URL+"/verifica/?cpf=123").contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isBadRequest());
    }

    @Test
    void deveRetornarOkQuandoVerificarCpf() throws Exception{
        doReturn(new StatusCpf("UNABLE_TO_VOTE")).when(mockedCpfService).verificaCpf("04953194063");
        mvc.perform(get(URL+"/verifica/?cpf=123").contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk());
    }

    @Test
    void NaoDeveCadastrarVotoQuandoSessaoNaoExiste() throws Exception{
        VotoDto voto = new VotoDto(1L, true, "00000000000");
        doThrow(new SessaoInexistenteException(1L)).when(mockedVotacaoService).cadastrarVoto(voto);
        mvc.perform(post(URL).contentType(MediaType.APPLICATION_JSON)
                        .content(voto.toString()))
                .andExpect(status().isBadRequest());
    }

    @Test
    void naoDevCadastrarVotoQuandoAssociadoJaVotou() throws Exception{
        VotoDto voto = new VotoDto(1L, true, "00000000000");
        doThrow(new AssociadoVotouException("00000000000")).when(mockedVotacaoService).cadastrarVoto(voto);
        mvc.perform(post(URL).contentType(MediaType.APPLICATION_JSON)
                        .content(voto.toString()))
                .andExpect(status().isBadRequest());
    }

    @Test
    void naoDeveCriarVotoQuandoPautaIdNaoExiste() throws Exception{
        VotoDto voto = new VotoDto(1L, true, "00000000000");
        doThrow(new PautaInexistenteException(1L)).when(mockedVotacaoService).cadastrarVoto(voto);
        mvc.perform(post(URL).contentType(MediaType.APPLICATION_JSON)
                        .content(voto.toString()))
                .andExpect(status().isBadRequest());
    }

    @Test
    void DeveCriarVotoComSucesso() throws Exception{
        mvc.perform(post(URL).contentType(MediaType.APPLICATION_JSON)
                        .content(leConteudoDoArquivo("__files/requestCadastrarSessao.json")))
                .andExpect(status().isOk());
    }

    @Test
    void deveContabilizarVotos() throws Exception{
        when(mockedVotacaoService.contabilizarVotos(1L)).thenReturn("aprovado");
        mvc.perform(get(URL+"/?pauta-id=1").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

    }

    @Test
    void naoDeveContabilizarVotosQuandoPautaIdForInvalida() throws Exception{
        doThrow(new PautaInexistenteException(1L)).when(mockedVotacaoService).contabilizarVotos(1L);
        mvc.perform(get(URL+"?pauta-id=1").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());

    }

    @SneakyThrows
    private byte[] leConteudoDoArquivo(String path) throws IOException {
        return new ClassPathResource(path).getInputStream().readAllBytes();
    }
}
