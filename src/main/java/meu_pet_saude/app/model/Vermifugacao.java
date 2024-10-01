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
import meu_pet_saude.app.dto.VermifugacaoDTO;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "tb_vermifugacao")
public class Vermifugacao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long idVerm;

    @Column(nullable = false)
    private String nomeMedic;

    @Column(nullable = false)
    private double peso;

    @Column(nullable = false)
    private double dosagem;

    @Column(nullable = false)
    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate data;

    @Column(nullable = false)
    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate proximaDose;
    
    @ManyToOne
    @JoinColumn(name = "animal_id")
    private Animal animal;

    public VermifugacaoDTO converterVermifugacaoDTO() {
        VermifugacaoDTO dto = new VermifugacaoDTO();

        dto.setVermifugo(nomeMedic);
        dto.setDataUltimaDose(proximaDose);
        dto.setDosagem(dosagem);
        dto.setDataProximaDose(proximaDose);

        return dto;
    }

}
