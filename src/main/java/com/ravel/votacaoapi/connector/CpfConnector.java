package com.ravel.votacaoapi.connector;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class CpfConnector implements CpfConnectorInterface{
    String URI = "http://run.mocky.io/v3/57f23672-c15f-48f8-90d3-d84ce00250b8/users/";

    public StatusCpf verificaCpfCooperado(String cpf){
        RestTemplate rest = new RestTemplate();
        ResponseEntity<StatusCpf> response = rest.getForEntity(URI+cpf, StatusCpf.class);
        return response.getBody();

    }
}