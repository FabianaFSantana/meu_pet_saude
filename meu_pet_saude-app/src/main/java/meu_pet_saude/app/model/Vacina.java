package meu_pet_saude.app.model;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "vacina")

public class Vacina {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long idVacina;

    @Column(nullable = false, unique = true)
    private String nomeVacina;

    @Column(nullable = false)
    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate dataDaUltimaDose;

    @Column(nullable = false)
    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate dataDaProximaDose;

    @Column(nullable = false)
    private String nomeDaClinica;

    @Column(nullable = false)
    private String nomeVeterinario;
}
