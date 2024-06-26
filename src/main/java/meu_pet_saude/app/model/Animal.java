package meu_pet_saude.app.model;

import java.time.LocalDate;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import meu_pet_saude.app.constant.Especie;
import meu_pet_saude.app.constant.Genero;

@Data
@NoArgsConstructor
@AllArgsConstructor 
@Entity(name = "animal")

public class Animal {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idAnimal;

    @Column(nullable = false)
    private String nome;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Especie especie;

    @Column(nullable = false)
    private String raca;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Genero genero;

    @JsonFormat(pattern = "dd/MM/yyyy")
    @Column(nullable = false)
    private LocalDate dataDeNascimento;

    @Column(nullable = false)
    private double peso;

    @Column(nullable = false)
    private String corDoPelo;

    @OneToMany
    private List<Vacina> vacinas;

    @OneToMany
    private List<Vermifugacao> vermifugos;

    @OneToMany
    private List<CarrapatoPulga> carrapaticidas;

    @OneToMany
    private List<Consulta> consultas;
    


}
