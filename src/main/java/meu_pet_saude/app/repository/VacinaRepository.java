package meu_pet_saude.app.repository;

import java.time.LocalDate;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import meu_pet_saude.app.model.Vacina;

@Repository
public interface VacinaRepository extends JpaRepository<Vacina, Long> {
    
    @Query("SELECT v FROM vacina v WHERE v.dataDaProximaDose = :dataDaProximaDose")
    Optional<Vacina> findByDataProximaDose(@Param("dataDaProximaDose") LocalDate dataDaProximaDose);
}
