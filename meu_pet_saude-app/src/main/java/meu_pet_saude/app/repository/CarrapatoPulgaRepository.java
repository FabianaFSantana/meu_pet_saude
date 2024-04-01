package meu_pet_saude.app.repository;

import java.time.LocalDate;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import meu_pet_saude.app.model.CarrapatoPulga;

@Repository
public interface CarrapatoPulgaRepository extends JpaRepository<CarrapatoPulga, Long> {
    
    @Query("SELECT c FROM carrapPulga c WHERE c.data = :data")
    Optional<CarrapatoPulga> findByDosagemData(@Param("data") LocalDate data);

}
