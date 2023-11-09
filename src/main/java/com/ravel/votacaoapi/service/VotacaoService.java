package com.ravel.votacaoapi.service;

import com.ravel.votacaoapi.dto.SessaoDto;
import com.ravel.votacaoapi.dto.VotoDto;
import com.ravel.votacaoapi.dto.PautaDto;
import com.ravel.votacaoapi.exception.AssociadoVotouException;
import com.ravel.votacaoapi.exception.PautaInexistenteException;
import com.ravel.votacaoapi.exception.SessaoAbertaException;
import com.ravel.votacaoapi.model.Pauta;
import com.ravel.votacaoapi.model.Sessao;
import com.ravel.votacaoapi.model.Voto;
import com.ravel.votacaoapi.repository.PautaRepository;
import com.ravel.votacaoapi.repository.SessaoRepository;
import com.ravel.votacaoapi.repository.VotoRepository;
import lombok.AllArgsConstructor;
import com.ravel.votacaoapi.connector.*;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.*;

@Service
@AllArgsConstructor
public class VotacaoService {
    PautaRepository pautaRepository;
    SessaoRepository sessaoRepository;
    VotoRepository votoRepository;
    CpfConnectorInterface cpfConnector;

    public Pauta cadastrarPauta(PautaDto pautaDto) {
        if(pautaDto.getDescricao() == null){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Dados inválidos para pauta");
        }
        return pautaRepository.save(new Pauta(pautaDto.getDescricao()));
    }

    public void abrirSessao(SessaoDto sessao) {
        if(sessao.getPautaId() == null){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Id da pauta não foi informado");
        }
        if( existePauta(sessao.getPautaId()) && !existeSessaoAberta(sessao.getPautaId())){
            int minutos = sessao.getDuracaoEmMinutos() == null ? 1 : sessao.getDuracaoEmMinutos();
            sessaoRepository.adicionarSessaoAPauta(Timestamp.from(Instant.now()),
                    Timestamp.from(Instant.now().plus(minutos, ChronoUnit.MINUTES)),
                    sessao.getPautaId());
        }else {
            throw new SessaoAbertaException(sessao.getPautaId());
        }
    }

    public String contabilizarVotos(Long pautaId) {
        List<Voto> votosPorPauta = new ArrayList<>();
        if(existePauta(pautaId)){
            votosPorPauta = votoRepository.buscaVotosPorPauta(pautaId);
        }
        return getResultadoVotacao(votosPorPauta);
    }
    public void cadastrarVoto(VotoDto votoDto) {
        if(votoDto.getPautaId() == null){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "A pauta deve ser informada");
        }
        if(existePauta(votoDto.getPautaId()) &&
                !associadoVotouEmPauta(votoDto.getPautaId(), votoDto.getCpfAssociado()) &&
                existeSessaoAberta(votoDto.getPautaId())
        ){
            votoRepository.cadastraVoto(votoDto.getPautaId(), votoDto.isVoto(), votoDto.getCpfAssociado());
        }else{
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY,
             "Não existe sessão aberta para esta pauta");
        }

    }

    public List<PautaDto> listarPautas() {
        return PautaDto.buildLista(pautaRepository.findAll());
    }

    public StatusCpf verificaCpf(String cpf) {
        verificaValidadadeCpf(cpf);
        return cpfConnector.verificaCpfCooperado(cpf);
    }

    private void verificaValidadadeCpf(String cpf){
        if (!cpf.matches("[0-9]+") || cpf.length() != 11) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Cpf informado está com formato incorreto");
        }
    }

    public List<PautaDto> listarPautasAbertas() {
        return PautaDto.buildLista(pautaRepository.listarPautasAbertas());
    }

    private boolean associadoVotouEmPauta(Long pautaId, String cpfAssociado) {
        Voto voto = votoRepository.buscaVotoDoAssociadoPorPauta(pautaId, cpfAssociado);
        if(voto != null){
            throw new AssociadoVotouException(cpfAssociado);
        }
        return false;
    }

    private boolean existePauta(Long pautaId) {
        Optional<Pauta> pauta = pautaRepository.findById(pautaId);
        if(pauta.isPresent()){
            throw new PautaInexistenteException(pautaId);
        }
        return true;
    }

    private boolean existeSessaoAberta(Long pautaId) {
        Sessao sessao = sessaoRepository.buscaSessaoAbertaParaPauta(pautaId);
        return sessao != null;
    }

    private Long getVotosContrarios(List<Voto> votosPorPauta) {
        return votosPorPauta.stream().filter(voto -> voto.isVotoAssociado() == false).count();
    }

    private Long getVotosFavoraveis(List<Voto> votosPorPauta) {
        return votosPorPauta.stream().filter(voto -> voto.isVotoAssociado() == true).count();
    }

    private String getResultadoVotacao(List<Voto> votosPorPauta){
        String resultado = new String();
        if (getVotosFavoraveis(votosPorPauta) > getVotosContrarios(votosPorPauta)){
            resultado = "aprovado";
        };
        if (getVotosFavoraveis(votosPorPauta) < getVotosContrarios(votosPorPauta)){
            resultado = "reprovado";
        }
        if (getVotosFavoraveis(votosPorPauta) == getVotosContrarios(votosPorPauta)){
            resultado = "empate";
        }
        return resultado;
    }
}
