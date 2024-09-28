package meu_pet_saude.app.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TutorAnimaisDTO {
    
    private String nomeTutor;
    private List<AnimalDTO> animais;
}
