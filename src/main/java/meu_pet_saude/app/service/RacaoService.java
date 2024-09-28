package meu_pet_saude.app.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import meu_pet_saude.app.dto.RacaoDTO;
import meu_pet_saude.app.model.Animal;
import meu_pet_saude.app.model.Racao;
import meu_pet_saude.app.repository.AnimalRepository;
import meu_pet_saude.app.repository.RacaoRepository;

@Service
public class RacaoService {

     public LocalDate calcularConsumoDaRacao(Long id) {
        Optional<Racao> racaoOptional = racaoRepository.findById(id);

        if (racaoOptional.isPresent()) {
            Racao racao = racaoOptional.get();
            Double pesoRacaoEmGramas = racao.getPeso() * 1000;
            Double consumoDiario = racao.getQuantidadeDiaria();
            int dias = (int) Math.floor(pesoRacaoEmGramas / consumoDiario);

            LocalDate dataUltimaCompra = racao.getDataUltimaCompra();
            LocalDate dataProximaCompra = dataUltimaCompra.plusDays(dias);

            racao.setDataProximaCompra(dataProximaCompra);
            racaoRepository.save(racao);  
            return dataProximaCompra;   
        }
        return null;
     }

    public RacaoDTO buscarRacaoPeloId(Long id) {
        Optional<Racao> racaoOptional = racaoRepository.findById(id);

        if (racaoOptional.isPresent()) {
            Racao racao = racaoOptional.get();
            return racao.converteRacaoDTO();
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

    public Racao atualizarRacao(Long id, Racao racao) {
        Optional<Racao> racaoOptional = racaoRepository.findById(id);

        if (racaoOptional.isPresent()) {
            Racao racaoEncontrada = racaoOptional.get();

            racaoEncontrada.setAnimal(racao.getAnimal());
            racaoEncontrada.setDataProximaCompra(racao.getDataProximaCompra());
            racaoEncontrada.setDataUltimaCompra(racao.getDataUltimaCompra());
            racaoEncontrada.setEspecie(racao.getEspecie());
            racaoEncontrada.setLoja(racao.getLoja());
            racaoEncontrada.setNomeRacao(racao.getNomeRacao());
            racaoEncontrada.setPeso(racao.getPeso());
            racaoEncontrada.setPreco(racao.getPreco());
            racaoEncontrada.setQuantidadeDiaria(racao.getQuantidadeDiaria());

            return racaoRepository.save(racaoEncontrada);
        }
        return null;
    }

    @Transactional
    public String removerRacao(Long idAnimal, Long id) {
        Animal animal = animalRepository.findById(idAnimal).orElse(null);
        Racao racao = racaoRepository.findById(id).orElse(null);

        if (animal == null) {
            return "Animal não encontrado!";
        }

        if (racao == null) {
            return "Ração não encontrada!";
        }

        List<Racao> racoes = animal.getRacoes();
        if (racoes.remove(racao)) {
            animalRepository.save(animal);
            racaoRepository.deleteById(id);
            return "Ração removida com sucesso!";
        }
        return "Ração não encontrada na lista do animal!";
    } 

    

    

    @Autowired
    private RacaoRepository racaoRepository;

    @Autowired
    private AnimalRepository animalRepository;
    
}
