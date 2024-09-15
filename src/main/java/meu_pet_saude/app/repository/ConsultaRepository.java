package meu_pet_saude.app.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import meu_pet_saude.app.model.Consulta;

@Repository
public interface ConsultaRepository extends JpaRepository<Consulta, Long> {
    
    List<Consulta> findByDataDaConsultaBetween(LocalDate dataInicial, LocalDate dataFinal);

}
