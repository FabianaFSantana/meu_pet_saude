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
import meu_pet_saude.app.model.Carrapaticida;
import meu_pet_saude.app.model.Tutor;
import meu_pet_saude.app.repository.AnimalRepository;
import meu_pet_saude.app.repository.CarrapaticidaRepository;
import meu_pet_saude.app.repository.TutorRepository;

@Service
public class CarrapaticidaService {

    public Carrapaticida buscarCarrapaticidaPeloId(Long idCarrap) {
        Optional<Carrapaticida> carrapOptional = carrapaticidaRepository.findById(idCarrap);

        if (carrapOptional.isPresent()) {
            Carrapaticida carrapaticida = carrapOptional.get();
            return carrapaticida;
        }
        return null;
    }
    
    public List<Carrapaticida> exibirListaCarrapaticidasDoAnimal(Long idAnimal) { 
        Optional<Animal> animOptional = animalRepository.findById(idAnimal);

        if (animOptional.isPresent()) {
            Animal animalEncont = animOptional.get();
            List<Carrapaticida> carrapaticidas = animalEncont.getCarrapaticidas();  
            return carrapaticidas;
        } else {
            return Collections.emptyList();
        }
    }

    public void removerCarrapaticidaDaLista(Long idAnimal, Long idCarrap) {
        
        Optional<Animal> animOptional = animalRepository.findById(idAnimal);
        if (animOptional.isPresent()) {
            Animal animalEncont = animOptional.get();
            
            Optional<CarrapaticidaService> carrOptional = carrapatoPulgaRepository.findById(idCarrap);
            if (carrOptional.isPresent()) {
                CarrapaticidaService carrapaticida = carrOptional.get();

                List<CarrapaticidaService> carrapaticidas = animalEncont.getCarrapaticidas();
                carrapaticidas.remove(carrapaticida);
                animalRepository.save(animalEncont);
                
            } else {
                throw new EntityNotFoundException("Carrapaticida não encontrado.");
            }
            
        } else {
            throw new EntityNotFoundException("Animal não econtrado.");
        }
    }

    public List<CarrapaticidaService> exibirListaDeCarrapaticidasProximaDose(Long idTutor, LocalDate proximaDose) {
        
        Optional<Tutor> tutorOptional = tutorRepository.findById(idTutor);
        List<CarrapaticidaService> carrapaticidasHoje = new ArrayList<>();

        if (tutorOptional.isPresent()) {
            Tutor tutorEncont = tutorOptional.get();
            List<Animal> animais = tutorEncont.getAnimais();

            for (Animal animal : animais) {
                if (!animal.getCarrapaticidas().isEmpty()) {
                    List<CarrapaticidaService> carrapaticidas = animal.getCarrapaticidas();

                    for (CarrapaticidaService carrapaticida : carrapaticidas){
                        if (carrapaticida.getProximaDose().equals(proximaDose)) {
                            carrapaticidasHoje.add(carrapaticida);
                        }
                    }
                    for (CarrapaticidaService carrapaticida : carrapaticidasHoje) {
                        emailService.enviarEmailDeRefocoDeCarrapaticida(tutorEncont, carrapaticida, animal);
                    }
                } 
            }
        }
        return carrapaticidasHoje;
    }

    @Autowired
    private AnimalRepository animalRepository;

    @Autowired 
    private CarrapaticidaRepository carrapaticidaRepository;

    @Autowired
    private EmailService emailService;

    @Autowired
    private TutorRepository tutorRepository;
    
}
