package meu_pet_saude.app.dto;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import meu_pet_saude.app.constant.TipoDeConsulta;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ConsultaDTO {
    
    private String nomeClinica;
    private String nomeVeterinario;
    private String nomeAnimal;
    private TipoDeConsulta tipoDeConsulta;
    private LocalDate dataDaConsulta;
    private String diagnostico;
    private String sintomas;
    private String medicamentos;
    private String posologia;


}
