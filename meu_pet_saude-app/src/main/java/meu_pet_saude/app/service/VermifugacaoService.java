package meu_pet_saude.app.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.persistence.EntityNotFoundException;
import meu_pet_saude.app.model.Animal;
import meu_pet_saude.app.model.Vermifugacao;
import meu_pet_saude.app.repository.AnimalRepository;
import meu_pet_saude.app.repository.VermifugacaoRepository;

@Service
public class VermifugacaoService {

    @Autowired
    private AnimalRepository animalRepository;

    @Autowired
    private VermifugacaoRepository vermifugacaoRepository;
    
    public void adicionarVermifugacao(Long idAnimal, Long idVerm) {
        
        Optional<Animal> animalOptional = animalRepository.findById(idAnimal);
        if (animalOptional.isPresent()) {
            Animal animal = animalOptional.get();

            Optional<Vermifugacao> vermOptional = vermifugacaoRepository.findById(idVerm);
            if (vermOptional.isPresent()) {
                Vermifugacao vermifugo = vermOptional.get();

                List<Vermifugacao> vermifugos = animal.getVermifugos();
                vermifugos.add(vermifugo);
                animalRepository.save(animal);
                
            } else {
                throw new EntityNotFoundException("Vermifugo não encntrado.");
            }
            
            
        } else {
            throw new EntityNotFoundException("Animal não encontrado.");
        }
        
    }
}
