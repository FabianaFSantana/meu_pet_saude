package meu_pet_saude.app.service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import meu_pet_saude.app.dto.CarrapaticidaDTO;
import meu_pet_saude.app.model.Animal;
import meu_pet_saude.app.model.Carrapaticida;
import meu_pet_saude.app.repository.AnimalRepository;
import meu_pet_saude.app.repository.CarrapaticidaRepository;

@Service
public class CarrapaticidaService {

    public LocalDate calcularProximaDoseCarrapaticida(Long idCarrapaticida) {
        Optional<Carrapaticida> carraptaicidaOptional = carrapaticidaRepository.findById(idCarrapaticida);

        if (carraptaicidaOptional.isPresent()) {
            Carrapaticida carrapaticida = carraptaicidaOptional.get();

            LocalDate ultimaDose = carrapaticida.getData();
            LocalDate proximaDose = ultimaDose.plusMonths(1);

            carrapaticida.setProximaDose(proximaDose);
            carrapaticidaRepository.save(carrapaticida);

            return proximaDose;
        }
        return null;
    }

    public CarrapaticidaDTO buscarCarrapaticidaPeloId(Long idCarrap) {
        Optional<Carrapaticida> carrapOptional = carrapaticidaRepository.findById(idCarrap);

        if (carrapOptional.isPresent()) {
            Carrapaticida carrapaticida = carrapOptional.get();
            return carrapaticida.converterCarrapaticidaDTO();
        }
        return null;
    }
    
    public List<CarrapaticidaDTO> exibirListaCarrapaticidasDoAnimal(Long idAnimal) { 
        Optional<Animal> animOptional = animalRepository.findById(idAnimal);

        if (animOptional.isPresent()) {
            Animal animalEncont = animOptional.get();

            List<CarrapaticidaDTO> carrapaticidas = animalEncont.getCarrapaticidas().stream().map(carrapaticida -> new CarrapaticidaDTO(carrapaticida.getAnimal().getNome(), carrapaticida.getNomeMedicamento(), carrapaticida.getPeso(), carrapaticida.getDosagem(), carrapaticida.getData(), carrapaticida.getProximaDose())).collect(Collectors.toList());

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
            carrapEncontrado.setProximaDose(carrapaticida.getProximaDose());

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

    
}
