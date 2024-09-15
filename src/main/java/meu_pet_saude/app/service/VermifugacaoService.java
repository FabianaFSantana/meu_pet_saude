package meu_pet_saude.app.service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import meu_pet_saude.app.model.Animal;
import meu_pet_saude.app.model.Vermifugacao;
import meu_pet_saude.app.repository.AnimalRepository;
import meu_pet_saude.app.repository.VermifugacaoRepository;

@Service
public class VermifugacaoService {


    public List<Vermifugacao> exibirListaDeVermifugosDoAnimal(Long idAnimal) {
        
        Optional<Animal> animOptional = animalRepository.findById(idAnimal);
        if (animOptional.isPresent()) {
            Animal animalEncont = animOptional.get();

            List<Vermifugacao> vermifugos =  animalEncont.getVermifugos();
            return vermifugos;
        } else {
            return Collections.emptyList();
        }
    }

    public Vermifugacao buscarVermifugacaoPorId(Long idVerm) {
        Optional<Vermifugacao> vermOptional = vermifugacaoRepository.findById(idVerm);

        if (vermOptional.isPresent()) {
            Vermifugacao vermifugo = vermOptional.get();
            return vermifugo;
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
