package tech.devluan.itau_backend.model;

import lombok.Getter;
import lombok.Setter;
import java.time.OffsetDateTime;

@Getter
@Setter
public class Transaction {
    private Double valor;
    private OffsetDateTime dataHora;
}
