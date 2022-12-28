package com.ravel.votacaoapi.controller;

import com.ravel.votacaoapi.dto.SessaoDto;
import com.ravel.votacaoapi.exception.PautaInexistenteException;
import com.ravel.votacaoapi.service.VotacaoService;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.io.IOException;

import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(SessaoController.class)
public class SessaoControllerTest {
    @Autowired
    private MockMvc mvc;
    @MockBean
    VotacaoService mockedVotacaoService;

    static final String URL = "/v1/sessao";

    @Test
    void DeveCriarSessaoComTempoDefinido() throws Exception{
        mvc.perform(post(URL).contentType(MediaType.APPLICATION_JSON)
                        .content(leConteudoDoArquivo("__files/requestCadastrarSessao.json")))
                .andExpect(status().isOk());
    }

    @Test
    void DeveCriarSessaoComTempoSemTempoDefinido() throws Exception{
        mvc.perform(post(URL).contentType(MediaType.APPLICATION_JSON)
                        .content(leConteudoDoArquivo("__files/requestCadastrarSessaoSemDuracao.json")))
                .andExpect(status().isOk());
    }

    @Test
    void NaoDeveCriarSessaoQuandoPautaIdNaoForEnviada() throws Exception{
        mvc.perform(post(URL).contentType(MediaType.APPLICATION_JSON)
                        .content(leConteudoDoArquivo("__files/requestCadastrarSessaoSemPautaId.json")))
                .andExpect(status().isBadRequest());
    }

    @Test
    void NaoDeveCriarSessaoQuandoPautaIdNaoExistir() throws Exception{
        SessaoDto sessao = new SessaoDto(1L, 5);
        doThrow(new PautaInexistenteException(1L)).when(mockedVotacaoService).abrirSessao(sessao);
        mvc.perform(post(URL).contentType(MediaType.APPLICATION_JSON)
                        .content(sessao.toString()))
                .andExpect(status().isBadRequest());
    }

    @Test
    void NaoDeveCriarSessaoQuandoSessaoJaExistir() throws Exception{
        SessaoDto sessao = new SessaoDto(1L, 5);
        doThrow(new PautaInexistenteException(1L)).when(mockedVotacaoService).abrirSessao(sessao);
        mvc.perform(post(URL).contentType(MediaType.APPLICATION_JSON)
                        .content(sessao.toString()))
                .andExpect(status().isBadRequest());
    }

    @SneakyThrows
    private byte[] leConteudoDoArquivo(String path) throws IOException {
        return new ClassPathResource(path).getInputStream().readAllBytes();
    }
}
