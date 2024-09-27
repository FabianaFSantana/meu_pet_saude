package meu_pet_saude.app.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import meu_pet_saude.app.dto.RacaoDTO;
import meu_pet_saude.app.model.Animal;
import meu_pet_saude.app.model.Racao;
import meu_pet_saude.app.repository.AnimalRepository;
import meu_pet_saude.app.repository.RacaoRepository;

@Service
public class RacaoService {

    public Racao buscarRacaoPeloId(Long id) {
        Optional<Racao> racaoOptional = racaoRepository.findById(id);

        if (racaoOptional.isPresent()) {
            return racaoOptional.get();
        }
        return null;
    }

    public List<RacaoDTO> listarRacoes() {

        return racaoRepository.findAll().stream().map(racao -> new RacaoDTO(racao.getNomeRacao(), racao.getEspecie(), racao.getDataUltimaCompra(), racao.getLoja(), racao.getDataProximaCompra(), racao.getPreco())).collect(Collectors.toList());
    }

    public List<RacaoDTO> buscarRacoesPorAnimal(Long idAnimal) {
        Optional<Animal> animalOptional = animalRepository.findById(idAnimal);

        if (animalOptional.isPresent()) {
            Animal animal = animalOptional.get();

            List<Racao> racoes = animal.getRacoes();
            return racoes.stream().map(racao -> new RacaoDTO(racao.getNomeRacao(), racao.getEspecie(), racao.getDataUltimaCompra(), racao.getLoja(), racao.getDataProximaCompra(), racao.getPreco())).collect(Collectors.toList());
        }
        return null;
    }

    public List<RacaoDTO> buscarRacoesPeloNome(String nome) {

        List<RacaoDTO> racoes = racaoRepository.findByNomeRacao(nome).stream().map(racao -> new RacaoDTO(racao.getNomeRacao(), racao.getEspecie(), racao.getDataUltimaCompra(), racao.getLoja(), racao.getDataProximaCompra(), racao.getPreco())).collect(Collectors.toList());
        return racoes;
    }

    public List<RacaoDTO> buscarRacoesPelaLoja(String loja) {
        List<RacaoDTO> racoes = racaoRepository.findByLoja(loja).stream().map(racao -> new RacaoDTO(racao.getNomeRacao(), racao.getEspecie(), racao.getDataUltimaCompra(), racao.getLoja(), racao.getDataProximaCompra(), racao.getPreco())).collect(Collectors.toList());
        return racoes;
    }

    public List<RacaoDTO> buscarRacoesPelaDataDeCompra(LocalDate dataInicio, LocalDate dataFinal) {
        List<RacaoDTO> racoes = racaoRepository.findByDataUltimaCompraBetween(dataInicio, dataFinal).stream().map(racao -> new RacaoDTO(racao.getNomeRacao(), racao.getEspecie(), racao.getDataUltimaCompra(), racao.getLoja(), racao.getDataProximaCompra(), racao.getPreco())).collect(Collectors.toList());
        return racoes;
    }

    

    

    @Autowired
    private RacaoRepository racaoRepository;

    @Autowired
    private AnimalRepository animalRepository;
    
}
