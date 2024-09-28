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
import meu_pet_saude.app.constant.Especie;
import meu_pet_saude.app.dto.RacaoDTO;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "tb_racoes")
public class Racao {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nomeRacao;

    @Column(nullable = false)
    private Especie especie;

    @Column(nullable = false)
    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate dataUltimaCompra;

    @Column(nullable = false)
    private Double peso;

    @Column(nullable = false)
    private Double preco;

    @Column(nullable = false)
    private String loja;

    @Column(nullable = false)
    private Double quantidadeDiaria;

    @Column(nullable = false)
    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate dataProximaCompra;
    
    @ManyToOne
    @JoinColumn(name = "animal_id")
    private Animal animal;

    public RacaoDTO converteRacaoDTO() {
        RacaoDTO dto = new RacaoDTO();

        dto.setNome(nomeRacao);
        dto.setEspecie(especie);
        dto.setLoja(loja);
        dto.setDataUltimaCompra(dataUltimaCompra);
        dto.setPreco(preco);
        dto.setDataProximaCompra(dataProximaCompra);

        return dto;
    }
}
