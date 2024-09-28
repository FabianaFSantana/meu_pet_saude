package meu_pet_saude.app.dto;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VacinaDTO {

    private String nome;
    private String vacina;
    private LocalDate dataAplicacao;
    private String veterinario;
    private LocalDate proximaDose;
}
