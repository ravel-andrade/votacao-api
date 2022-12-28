package utils;

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
}
