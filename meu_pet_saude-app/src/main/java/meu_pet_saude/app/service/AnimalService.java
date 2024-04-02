package meu_pet_saude.app.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.persistence.EntityNotFoundException;
import meu_pet_saude.app.model.Animal;
import meu_pet_saude.app.model.Tutor;
import meu_pet_saude.app.repository.AnimalRepository;
import meu_pet_saude.app.repository.TutorRepository;

@Service
public class AnimalService {
    
    @Autowired
    private AnimalRepository animalRepository;

    @Autowired
    private TutorRepository tutorRepository;

    public void adicionarAnimalNaListaDeAnimaisDeTutor(Long idTutor, Long idAnimal) {
        Optional<Tutor> tutorOptional = tutorRepository.findById(idTutor);
        if (tutorOptional.isPresent()) {
            Tutor tutorEncont = tutorOptional.get();

            Optional<Animal> animalOptional = animalRepository.findById(idAnimal);
            if (animalOptional.isPresent()) {
                Animal animalEncont = animalOptional.get();

                List<Animal> animais = tutorEncont.getAnimais();
                animais.add(animalEncont);
                tutorRepository.save(tutorEncont);
                
            } else {
                throw new EntityNotFoundException("Animal não encontrado.");
            }
            
        } else {
            throw new EntityNotFoundException("Tutor não encontrado.");
        }
    }

    public void removerAnimalDaLista(Long idTutor, Long idAnimal) {
        Optional<Tutor> tutorOptional = tutorRepository.findById(idTutor);
        if (tutorOptional.isPresent()) {
            Tutor tutorEncont = tutorOptional.get();

            Optional<Animal> animalOptional = animalRepository.findById(idAnimal);
            if (animalOptional.isPresent()) {
                Animal animalEncont = animalOptional.get();

                List<Animal> animais = tutorEncont.getAnimais();
                animais.remove(animalEncont);
                tutorRepository.save(tutorEncont);
                
            } else {
                throw new EntityNotFoundException("Animal não encontrado.");
            }
            
        } else {
            throw new EntityNotFoundException("Tutor não encontrado.");
        }
    }
}
