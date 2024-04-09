package meu_pet_saude.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import meu_pet_saude.app.model.Vacina;

@Repository
public interface VacinaRepository extends JpaRepository<Vacina, Long> {
    
}
