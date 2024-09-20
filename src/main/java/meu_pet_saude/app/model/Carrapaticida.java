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

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "tb_carrapaticidas")
public class Carrapaticida {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long idCarrapaticida;

    @Column(nullable = false)
    private String nomeMedicamento;

    @Column(nullable = false)
    private double peso;

    @Column(nullable = false)
    private double dosagem;

    @Column(nullable = false)
    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate data;

    @ManyToOne
    @JoinColumn(name = "animal_id")
    private Animal animal;
    
}
