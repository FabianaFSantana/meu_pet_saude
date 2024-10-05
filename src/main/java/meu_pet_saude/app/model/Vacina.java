package meu_pet_saude.app.model;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import meu_pet_saude.app.dto.VacinaDTO;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "tb_vacinas")

public class Vacina {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long idVacina;

    @Column(nullable = false)
    private String nomeVacina;

    @Column(nullable = false)
    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate data;

    @Column(nullable = false)
    private String nomeDaClinica;

    @Column(nullable = false)
    private String nomeVeterinario;

    @Column(nullable = false)
    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate proximaDose;

    @ManyToOne
    @JoinColumn(name = "animal_id", nullable = false)
    private Animal animal;

    public VacinaDTO converterVacinaDTO() {
        VacinaDTO dto = new VacinaDTO();
        
        dto.setNomeAnimal(animal.getNome());
        dto.setVacina(nomeVacina);
        dto.setDataAplicacao(data);
        dto.setVeterinario(nomeVeterinario);
        dto.setProximaDose(proximaDose);

        return dto;
    }
    
}
