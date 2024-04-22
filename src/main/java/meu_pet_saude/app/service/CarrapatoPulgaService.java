package meu_pet_saude.app.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.persistence.EntityNotFoundException;
import meu_pet_saude.app.model.Animal;
import meu_pet_saude.app.model.CarrapatoPulga;
import meu_pet_saude.app.model.Tutor;
import meu_pet_saude.app.repository.AnimalRepository;
import meu_pet_saude.app.repository.CarrapatoPulgaRepository;
import meu_pet_saude.app.repository.TutorRepository;

@Service
public class CarrapatoPulgaService {

    @Autowired
    private AnimalRepository animalRepository;

    @Autowired 
    private CarrapatoPulgaRepository carrapatoPulgaRepository;

    @Autowired
    private EmailService emailService;

    @Autowired
    private TutorRepository tutorRepository;

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

    public void removerCarrapaticidaDaLista(Long idAnimal, Long idCarrap) {
        
        Optional<Animal> animOptional = animalRepository.findById(idAnimal);
        if (animOptional.isPresent()) {
            Animal animalEncont = animOptional.get();
            
            Optional<CarrapatoPulga> carrOptional = carrapatoPulgaRepository.findById(idCarrap);
            if (carrOptional.isPresent()) {
                CarrapatoPulga carrapaticida = carrOptional.get();

                List<CarrapatoPulga> carrapaticidas = animalEncont.getCarrapaticidas();
                carrapaticidas.remove(carrapaticida);
                animalRepository.save(animalEncont);
                
            } else {
                throw new EntityNotFoundException("Carrapaticida não encontrado.");
            }
            
        } else {
            throw new EntityNotFoundException("Animal não econtrado.");
        }
    }

    public List<CarrapatoPulga> exibirListaDeCarrapaticidasProximaDose(Long idTutor, LocalDate proximaDose) {
        
        Optional<Tutor> tutorOptional = tutorRepository.findById(idTutor);
        List<CarrapatoPulga> carrapaticidasHoje = new ArrayList<>();

        if (tutorOptional.isPresent()) {
            Tutor tutorEncont = tutorOptional.get();
            List<Animal> animais = tutorEncont.getAnimais();

            for (Animal animal : animais) {
                if (!animal.getCarrapaticidas().isEmpty()) {
                    List<CarrapatoPulga> carrapaticidas = animal.getCarrapaticidas();

                    for (CarrapatoPulga carrapaticida : carrapaticidas){
                        if (carrapaticida.getProximaDose().equals(proximaDose)) {
                            carrapaticidasHoje.add(carrapaticida);
                        }
                    }
                    for (CarrapatoPulga carrapaticida : carrapaticidasHoje) {
                        emailService.enviarEmailDeRefocoDeCarrapaticida(tutorEncont, carrapaticida, animal);
                    }
                } 
            }
        }
        return carrapaticidasHoje;
    }
    
}
