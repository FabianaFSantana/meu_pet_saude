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
import meu_pet_saude.app.constant.TipoDeConsulta;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "tb_consultas")

public class Consulta {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long idConsulta;

   @Column(nullable = false)
    private String nomeDaClinica;

    @Column(nullable = false)
    private String veterinario;

    @Column(nullable = false)
    private TipoDeConsulta tipoDeConsulta;

    @Column(nullable = false)
    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate dataDaConsulta;

    @Column(nullable = false)
    private String sintomas;

    @Column(columnDefinition = "TEXT")
    private String diagnostico;

    private String medicacao;

    @Column(columnDefinition = "TEXT")
    private String posologia;

    @ManyToOne
    @JoinColumn(name = "animal_id")
    private Animal animal;
}
