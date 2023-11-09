package com.ravel.votacaoapi.connector;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.*;

@Getter
public class StatusCpf{
    String status;

    @JsonCreator
    public StatusCpf(@JsonProperty("status") String status){
        this.status = status;
    }
}