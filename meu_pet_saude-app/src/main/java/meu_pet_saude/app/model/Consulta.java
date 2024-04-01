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
import meu_pet_saude.app.constant.TipoDeConsulta;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "consulta")

public class Consulta {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long idConsulta;

    @Column(nullable = false)
    private String clinica;

    @Column(nullable = false)
    private String veterinario;

    @Column(nullable = false)
    private TipoDeConsulta tipoDeConsulta;

    @Column(nullable = false)
    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate data;

    @Column(nullable = false)
    private String sintomas;

    @Column(nullable = false)
    private String diagnostico;

    @Column(nullable = false)
    private String medicacao;

    @Column(nullable = false)
    private String posologia;
}
