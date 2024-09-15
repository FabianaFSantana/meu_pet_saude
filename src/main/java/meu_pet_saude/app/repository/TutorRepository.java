package meu_pet_saude.app.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import meu_pet_saude.app.model.Tutor;

@Repository
public interface TutorRepository extends JpaRepository<Tutor, Long> {
    
    Optional<Tutor> findByEmail(String email);

    @Query("SELECT c FROM tutor c WHERE DAY(c.dataNascimento) = ?1 AND MONTH(c.dataNascimento) = ?2")
    List<Tutor> findByDiaMesNascimento(int dia, int mes);
}
