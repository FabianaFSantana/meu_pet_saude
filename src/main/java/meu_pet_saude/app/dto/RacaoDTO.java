package meu_pet_saude.app.dto;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import meu_pet_saude.app.constant.Especie;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class RacaoDTO {
    private String nome;
    private Especie especie;
    private LocalDate dataUltimaCompra;
    private String loja;
    private LocalDate dataProximaCompra;
    private Double preco;
}
