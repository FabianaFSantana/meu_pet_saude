package meu_pet_saude.app.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import meu_pet_saude.app.model.Vermifugacao;
import java.time.LocalDate;


@Repository
public interface VermifugacaoRepository extends JpaRepository<Vermifugacao, Long> {
    
    List<Vermifugacao> findByData(LocalDate data);
}
