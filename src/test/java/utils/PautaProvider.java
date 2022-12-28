package utils;

import com.ravel.votacaoapi.dto.PautaDto;
import com.ravel.votacaoapi.model.Pauta;
import com.ravel.votacaoapi.model.Voto;

import java.util.ArrayList;
import java.util.List;

public class PautaProvider {

    public static List<Pauta> buildPautasAbertas() {
        List<Pauta> pauta = new ArrayList<>();
        pauta.add(new Pauta(1L, "placeholder1"));
        pauta.add(new Pauta(2L, "placeholder2"));
        pauta.add(new Pauta(3L, "placeholder3"));

        return pauta;
    }

    public static List<Pauta> buildPautas() {
        List<Pauta> pauta = new ArrayList<>();
        pauta.add(new Pauta(1L, "placeholder1"));
        pauta.add(new Pauta(2L, "placeholder2"));
        pauta.add(new Pauta(3L, "placeholder3"));
        pauta.add(new Pauta(4L, "placeholder1"));
        pauta.add(new Pauta(5L, "placeholder2"));
        pauta.add(new Pauta(6L, "placeholder3"));

        return pauta;
    }

    public static List<PautaDto> getPautas() {
        List<PautaDto> pauta = new ArrayList<>();
        pauta.add(new PautaDto(1L, "placeholder1"));
        pauta.add(new PautaDto(2L, "placeholder2"));
        pauta.add(new PautaDto(3L, "placeholder3"));
        return pauta;
    }
}
