package meu_pet_saude.app.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AutenticacaoDTO {

    @NotNull(message = "Digite um e-mail válido!")
    @NotBlank(message = "É obrigatório informar o email!")
    private String username;

    @NotNull(message = "Digite uma senha válida!")
    @NotBlank(message = "É obrigatório informar a senha!")
    private String password;

}
