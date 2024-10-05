package meu_pet_saude.app.dto;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CarrapaticidaDTO {
    
    private String nomeAnimal;
    private String carrapaticida;
    private double pesoAnimal;
    private double dosagem;
    private LocalDate ultimaDose;
    private LocalDate proximaDose;
}
