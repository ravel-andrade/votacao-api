package utils;

import com.ravel.votacaoapi.dto.PautaDto;
import com.ravel.votacaoapi.dto.VotoDto;
import com.ravel.votacaoapi.model.Pauta;
import com.ravel.votacaoapi.model.Voto;

import java.util.ArrayList;
import java.util.List;

public class VotoProvider {
    public static final Voto VOTO = new Voto(1, true, "00000000000", new Pauta(1L, "placeholder"));

    public static List<Voto> buildVotosEsperados() {
        Pauta pauta = new Pauta(1L, "placeholder");
        List<Voto> votos = new ArrayList<>();
        votos.add(new Voto(1, true, "00000000000", pauta));
        votos.add(new Voto(2, false, "00000000001", pauta));
        votos.add(new Voto(3, true, "00000000002", pauta));
        votos.add(new Voto(4, false, "00000000003", pauta));
        votos.add(new Voto(5, true, "00000000004", pauta));
        return votos;
    }
}
