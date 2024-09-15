package meu_pet_saude.app.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import meu_pet_saude.app.model.Endereco;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TutorDTO {
    private String nome;
    private String telefone;
    private String email;
    private Endereco endereco;
    private Boolean administrador;
    private Boolean usuarioExterno;
    private String token;
}
