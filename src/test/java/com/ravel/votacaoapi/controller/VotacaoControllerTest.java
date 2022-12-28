package com.ravel.votacaoapi.controller;

import com.ravel.votacaoapi.dto.PautaDto;
import com.ravel.votacaoapi.dto.SessaoDto;
import com.ravel.votacaoapi.dto.VotoDto;
import com.ravel.votacaoapi.exception.AssociadoVotouException;
import com.ravel.votacaoapi.exception.PautaInexistenteException;
import com.ravel.votacaoapi.exception.SessaoInexistenteException;
import com.ravel.votacaoapi.model.Pauta;
import com.ravel.votacaoapi.model.Sessao;
import com.ravel.votacaoapi.model.Voto;
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

import static org.hamcrest.Matchers.equalTo;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(VotacaoController.class)
public class VotacaoControllerTest {

    @Autowired
    private MockMvc mvc;
    @MockBean
    VotacaoService mockedVotacaoService;

    @Test
    void deveCriarPautaComSucesso() throws Exception{
        Pauta pauta =  new Pauta(1L,"placeholder");
        when(mockedVotacaoService.cadastrarPauta(new Pauta("placeholder"))).thenReturn(pauta);

        mvc.perform(post("/cadastrar-pauta").contentType(MediaType.APPLICATION_JSON)
                        .content(leConteudoDoArquivo("__files/requestCadastrarPauta.json")))
                        .andExpect(status().isOk());

        verify(mockedVotacaoService).cadastrarPauta(new Pauta("placeholder"));
    }

    @Test
    void naoDeveCriarPautaQuandoNaoPassaDescricao() throws Exception{
        mvc.perform(post("/cadastrar-pauta").contentType(MediaType.APPLICATION_JSON)
                        .content(leConteudoDoArquivo("__files/requestCadastrarPautaSemDescricao.json")))
                    .andExpect(status().isBadRequest());
    }

    @Test
    void DeveCriarSessaoComTempoDefinido() throws Exception{
        mvc.perform(post("/abrir-sessao").contentType(MediaType.APPLICATION_JSON)
                        .content(leConteudoDoArquivo("__files/requestCadastrarSessao.json")))
                .andExpect(status().isOk());
    }

    @Test
    void DeveCriarSessaoComTempoSemTempoDefinido() throws Exception{
        mvc.perform(post("/abrir-sessao").contentType(MediaType.APPLICATION_JSON)
                        .content(leConteudoDoArquivo("__files/requestCadastrarSessaoSemDuracao.json")))
                .andExpect(status().isOk());
    }

    @Test
    void NaoDeveCriarSessaoQuandoPautaIdNaoForEnviada() throws Exception{
        mvc.perform(post("/abrir-sessao").contentType(MediaType.APPLICATION_JSON)
                        .content(leConteudoDoArquivo("__files/requestCadastrarSessaoSemPautaId.json")))
                .andExpect(status().isBadRequest());
    }

    @Test
    void NaoDeveCriarSessaoQuandoPautaIdNaoExistir() throws Exception{
        SessaoDto sessao = new SessaoDto(1L, 5);
        doThrow(new PautaInexistenteException(1L)).when(mockedVotacaoService).abrirSessao(sessao);
        mvc.perform(post("/abrir-sessao").contentType(MediaType.APPLICATION_JSON)
                        .content(sessao.toString()))
                .andExpect(status().isBadRequest());
    }

    @Test
    void NaoDeveCriarSessaoQuandoSessaoJaExistir() throws Exception{
        SessaoDto sessao = new SessaoDto(1L, 5);
        doThrow(new PautaInexistenteException(1L)).when(mockedVotacaoService).abrirSessao(sessao);
        mvc.perform(post("/abrir-sessao").contentType(MediaType.APPLICATION_JSON)
                        .content(sessao.toString()))
                .andExpect(status().isBadRequest());
    }

    @Test
    void NaoDeveCadastrarVotoQuandoSessaoNaoExiste() throws Exception{
        VotoDto voto = new VotoDto(1L, true, "00000000000");
        doThrow(new SessaoInexistenteException(1L)).when(mockedVotacaoService).cadastrarVoto(voto);
        mvc.perform(post("/votar").contentType(MediaType.APPLICATION_JSON)
                        .content(voto.toString()))
                .andExpect(status().isBadRequest());
    }

    @Test
    void naoDevCadastrarVotoQuandoAssociadoJaVotou() throws Exception{
        VotoDto voto = new VotoDto(1L, true, "00000000000");
        doThrow(new AssociadoVotouException("00000000000")).when(mockedVotacaoService).cadastrarVoto(voto);
        mvc.perform(post("/votar").contentType(MediaType.APPLICATION_JSON)
                        .content(voto.toString()))
                .andExpect(status().isBadRequest());
    }

    @Test
    void naoDeveCriarVotoQuandoPautaIdNaoExiste() throws Exception{
        VotoDto voto = new VotoDto(1L, true, "00000000000");
        doThrow(new PautaInexistenteException(1L)).when(mockedVotacaoService).cadastrarVoto(voto);
        mvc.perform(post("/votar").contentType(MediaType.APPLICATION_JSON)
                        .content(voto.toString()))
                .andExpect(status().isBadRequest());
    }

    @Test
    void DeveCriarVotoComSucesso() throws Exception{
        mvc.perform(post("/votar").contentType(MediaType.APPLICATION_JSON)
                        .content(leConteudoDoArquivo("__files/requestCadastrarSessao.json")))
                .andExpect(status().isOk());
    }

    @Test
    void deveContabilizarVotos() throws Exception{
        when(mockedVotacaoService.contabilizarVotos(1L)).thenReturn("aprovado");
        mvc.perform(get("/contabilizar?pauta-id=1").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

    }

    @Test
    void naoDeveContabilizarVotosQuandoPautaIdForInvalida() throws Exception{
        doThrow(new PautaInexistenteException(1L)).when(mockedVotacaoService).contabilizarVotos(1L);
        mvc.perform(get("/contabilizar?pauta-id=1").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());

    }

    @Test
    void deveBuscarTodasAsPautas() throws Exception{
        mvc.perform(get("/pautas").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    void deveBuscarTodasAsPautasAbertas() throws Exception{
        mvc.perform(get("/pautas?abertas=true").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }



    @SneakyThrows
    private byte[] leConteudoDoArquivo(String path) throws IOException {
        return new ClassPathResource(path).getInputStream().readAllBytes();
    }
}
