package com.ravel.votacaoapi.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ravel.votacaoapi.dto.PautaDto;
import com.ravel.votacaoapi.exception.PautaInexistenteException;
import com.ravel.votacaoapi.model.Pauta;
import com.ravel.votacaoapi.service.VotacaoService;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import utils.PautaProvider;

import java.io.IOException;

import static org.hamcrest.Matchers.equalTo;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PautaController.class)
public class PautaControllerTest {
    @Autowired
    private MockMvc mvc;
    @MockBean
    VotacaoService mockedVotacaoService;

    static final String URL = "/v1/pauta";
    @Test
    void deveCriarPautaComSucesso() throws Exception{
        Pauta pauta =  new Pauta(1L,"placeholder");

        when(mockedVotacaoService.cadastrarPauta(new PautaDto("placeholder"))).thenReturn(pauta);
        mvc.perform(post(URL).contentType(MediaType.APPLICATION_JSON)
                        .content(leConteudoDoArquivo("__files/requestCadastrarPauta.json")))
                .andExpect(status().isOk());
    }

    @Test
    void naoDeveCriarPautaQuandoNaoPassaDescricao() throws Exception{
        mvc.perform(post(URL).contentType(MediaType.APPLICATION_JSON)
                        .content(leConteudoDoArquivo("__files/requestCadastrarPautaSemDescricao.json")))
                .andExpect(status().isBadRequest());
    }

    @Test
    void deveBuscarTodasAsPautas() throws Exception{
        when(mockedVotacaoService.listarPautas()).thenReturn(PautaProvider.getPautas());
        mvc.perform(get(URL).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("[0].descricao", equalTo("placeholder1")))
                .andExpect(jsonPath("[1].descricao", equalTo("placeholder2")))
                .andExpect(jsonPath("[2].descricao", equalTo("placeholder3")));
    }

    @Test
    void deveBuscarTodasAsPautasAbertas() throws Exception{
        when(mockedVotacaoService.listarPautasAbertas()).thenReturn(PautaProvider.getPautas());
        mvc.perform(get(URL+"?abertas=true").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("[0].descricao", equalTo("placeholder1")))
                .andExpect(jsonPath("[1].descricao", equalTo("placeholder2")))
                .andExpect(jsonPath("[2].descricao", equalTo("placeholder3")));
    }

    @SneakyThrows
    private byte[] leConteudoDoArquivo(String path) throws IOException {
        return new ClassPathResource(path).getInputStream().readAllBytes();
    }
}
