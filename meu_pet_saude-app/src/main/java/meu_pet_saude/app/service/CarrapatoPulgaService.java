package meu_pet_saude.app.service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import javax.swing.text.html.Option;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.persistence.EntityNotFoundException;
import meu_pet_saude.app.model.Animal;
import meu_pet_saude.app.model.CarrapatoPulga;
import meu_pet_saude.app.repository.AnimalRepository;
import meu_pet_saude.app.repository.CarrapatoPulgaRepository;

@Service
public class CarrapatoPulgaService {

    @Autowired
    private AnimalRepository animalRepository;

    @Autowired 
    private CarrapatoPulgaRepository carrapatoPulgaRepository;

    public void adicionarCarrapaticidaNaLista(Long idAnimal, Long idCarrap) {

        Optional<Animal> animOptional = animalRepository.findById(idAnimal);
        if (animOptional.isPresent()) {
            Animal animalEncont = animOptional.get();

            Optional<CarrapatoPulga> carrOptional = carrapatoPulgaRepository.findById(idCarrap);
            if (carrOptional.isPresent()) {
                CarrapatoPulga carrapaticida = carrOptional.get();

                List<CarrapatoPulga> carrapaticidas = animalEncont.getCarrapaticidas();
                carrapaticidas.add(carrapaticida);
                animalRepository.save(animalEncont);
                
            } else {
                throw new EntityNotFoundException("Carrapaticida não encontrado.");
            }
        } else {
            throw new EntityNotFoundException("Animal não econtrado");
        }
    }

    public List<CarrapatoPulga> exibirListaCarrapaticidasDoAnimal(Long idAnimal) { 
        Optional<Animal> animOptional = animalRepository.findById(idAnimal);

        if (animOptional.isPresent()) {
            Animal animalEncont = animOptional.get();
            return animalEncont.getCarrapaticidas();  
        } else {
            return Collections.emptyList();
        }
    }
    
}
