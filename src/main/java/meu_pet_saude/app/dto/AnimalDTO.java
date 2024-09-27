package meu_pet_saude.app.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import meu_pet_saude.app.constant.Especie;
import meu_pet_saude.app.constant.Genero;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AnimalDTO {
    private String nome;
    private Especie especie;
    private String raca;
    private Genero genero;
}
