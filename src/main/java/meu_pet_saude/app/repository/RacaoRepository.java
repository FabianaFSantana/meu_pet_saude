package meu_pet_saude.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import meu_pet_saude.app.model.Animal;
import meu_pet_saude.app.model.Racao;
import java.util.List;
import java.util.Optional;
import java.time.LocalDate;
import meu_pet_saude.app.constant.Especie;




@Repository
public interface RacaoRepository extends JpaRepository<Racao, Long> {
    
    List<Racao> findByNomeRacao(String nomeRacao);

    List<Racao> findByLoja(String loja);

    List<Racao> findByDataUltimaCompraBetween (LocalDate dataInicio, LocalDate dataFinal);

    List<Racao> findByEspecie(Especie especie);

    List<Racao> findByDataProximaCompra(LocalDate dataProximaCompra);
}
