package meu_pet_saude.app.dto;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VermifugacaoDTO {
    private String vermifugo;
    private LocalDate DataUltimaDose;
    private double dosagem;
    private LocalDate DataProximaDose;
    

}
