package meu_pet_saude.app.service;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import meu_pet_saude.app.dto.VermifugacaoDTO;
import meu_pet_saude.app.model.Animal;
import meu_pet_saude.app.model.Vermifugacao;
import meu_pet_saude.app.repository.AnimalRepository;
import meu_pet_saude.app.repository.VermifugacaoRepository;

@Service
public class VermifugacaoService {

    public LocalDate calcularProximaDoseVermifugacao(Long idVerm) {
        Optional<Vermifugacao> vermifugacaoOptional = vermifugacaoRepository.findById(idVerm);

        if (vermifugacaoOptional.isPresent()) {
            Vermifugacao vermifugo = vermifugacaoOptional.get();

            LocalDate dataDose = vermifugo.getData();
            LocalDate proximaDose = dataDose.plusDays(180);

            vermifugo.setProximaDose(proximaDose);
            vermifugacaoRepository.save(vermifugo);

            return proximaDose;
        }
        return null;
    }

    public List<VermifugacaoDTO> exibirListaDeVermifugosDoAnimal(Long idAnimal) {
        
        Optional<Animal> animOptional = animalRepository.findById(idAnimal);
        if (animOptional.isPresent()) {
            Animal animalEncont = animOptional.get();

            List<VermifugacaoDTO> vermifugos =  animalEncont.getVermifugos().stream().map(vermifugo -> new VermifugacaoDTO(vermifugo.getNomeMedic(), vermifugo.getData(), vermifugo.getDosagem(), vermifugo.getProximaDose())).collect(Collectors.toList());
            return vermifugos;
        } else {
            return Collections.emptyList();
        }
    }

    public VermifugacaoDTO buscarVermifugacaoPorId(Long idVerm) {
        Optional<Vermifugacao> vermOptional = vermifugacaoRepository.findById(idVerm);

        if (vermOptional.isPresent()) {
            Vermifugacao vermifugo = vermOptional.get();
            return vermifugo.converterVermifugacaoDTO();
        }
        return null;
    }

    public Vermifugacao atualizarDadosVermifufo(Long idVerm, Vermifugacao vermifugacao) {
        Optional<Vermifugacao> vermOptional = vermifugacaoRepository.findById(idVerm);

        if (vermOptional.isPresent()) {
            Vermifugacao vermEncontrada = vermOptional.get();

            vermEncontrada.setNomeMedic(vermifugacao.getNomeMedic());
            vermEncontrada.setPeso(vermifugacao.getPeso());
            vermEncontrada.setDosagem(vermifugacao.getDosagem());
            vermEncontrada.setData(vermifugacao.getData());

            return vermifugacaoRepository.save(vermEncontrada);
        }
        return null;

    }
    
    @Transactional
    public String removerVermifugo(Long idAnimal, Long idVerm) {
            Animal animal = animalRepository.findById(idAnimal).orElse(null);
            Vermifugacao vermifugo = vermifugacaoRepository.findById(idVerm).orElse(null);

            if (animal == null) {
                return "Animal não encontrado!";
            }
            
            if (vermifugo == null) {
                return "Vermífugo não encontrado!";
            }

            List<Vermifugacao> vermifugos = animal.getVermifugos();
            if (vermifugos.remove(vermifugo)) {
                animalRepository.save(animal);
                vermifugacaoRepository.deleteById(idVerm);
                return "Vermífugo removido com sucesso!";
            }
            return "Vermífugo não encontrado na lista do animal!";
    }

   

    @Autowired
    private AnimalRepository animalRepository;

    @Autowired
    private VermifugacaoRepository vermifugacaoRepository;

}
