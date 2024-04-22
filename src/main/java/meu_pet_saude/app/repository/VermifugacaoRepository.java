package meu_pet_saude.app.repository;

import java.time.LocalDate;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import meu_pet_saude.app.model.Vermifugacao;

@Repository
public interface VermifugacaoRepository extends JpaRepository<Vermifugacao, Long> {
    
    @Query("SELECT v FROM vermifugacao v WHERE v.proximaDose = :proximaDose")
    Optional<Vermifugacao> findByDataProximaDose(@Param("proximaDose") LocalDate proximaDose);
}
