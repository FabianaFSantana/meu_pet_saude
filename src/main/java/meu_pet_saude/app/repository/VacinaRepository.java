package meu_pet_saude.app.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import meu_pet_saude.app.model.Vacina;
import java.time.LocalDate;


@Repository
public interface VacinaRepository extends JpaRepository<Vacina, Long> {
    
    
    List<Vacina> findByData(LocalDate data);

}
