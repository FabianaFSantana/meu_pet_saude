package meu_pet_saude.app.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import meu_pet_saude.app.model.Carrapaticida;
import java.time.LocalDate;


@Repository
public interface CarrapaticidaRepository extends JpaRepository<Carrapaticida, Long> {

    List<Carrapaticida> findByData(LocalDate data);
    List<Carrapaticida> findByProximaDose(LocalDate proximaDose);
}
