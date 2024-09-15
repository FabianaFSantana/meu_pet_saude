package meu_pet_saude.app.service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import meu_pet_saude.app.model.Animal;
import meu_pet_saude.app.model.Carrapaticida;
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

    public Carrapaticida atualizarDadosCarrapaticida(Long idCarrapaticida, Carrapaticida carrapaticida) {
        Optional<Carrapaticida> carrapOptional = carrapaticidaRepository.findById(idCarrapaticida);

        if (carrapOptional.isPresent()) {
            Carrapaticida carrapEncontrado = carrapOptional.get();

            carrapEncontrado.setData(carrapaticida.getData());
            carrapEncontrado.setDosagem(carrapaticida.getDosagem());
            carrapEncontrado.setNomeMedicamento(carrapaticida.getNomeMedicamento());
            carrapEncontrado.setPeso(carrapaticida.getPeso());

            carrapaticidaRepository.save(carrapEncontrado);
            return carrapEncontrado;
        }
        return null;
    }

    @Transactional
    public String excluirCarrapaticida(Long idAnimal, Long idCarrapaticida) {

        Animal animal = animalRepository.findById(idAnimal).orElse(null);
        Carrapaticida carrapaticida = carrapaticidaRepository.findById(idCarrapaticida).orElse(null);

        if (animal == null) {
            return "Animal não encontrado";
        }

        if (carrapaticida == null) {
            return "Carrapaticida não encontrado!";
        }

        List<Carrapaticida> carrapaticidas = animal.getCarrapaticidas();
        if (carrapaticidas.remove(carrapaticida)) {
            animalRepository.save(animal);
            carrapaticidaRepository.deleteById(idCarrapaticida);
            return "Carrapaticida excluído com sucesso!";
        }
        return "Carrapaticida não encontrado na lista do animal.";

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
