package meu_pet_saude.app.service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.persistence.EntityNotFoundException;
import meu_pet_saude.app.model.Animal;
import meu_pet_saude.app.model.Consulta;
import meu_pet_saude.app.repository.AnimalRepository;
import meu_pet_saude.app.repository.ConsultaRepository;

@Service
public class ConsultaService {
    @Autowired
    private ConsultaRepository consultaRepository;

    @Autowired
    private AnimalRepository animalRepository;

    public void adicionarConsultaNaLista(Long idAnimal, Long idConsulta) {

        Optional<Animal> animOptional = animalRepository.findById(idAnimal);
        if (animOptional.isPresent()) {
            Animal animEncont = animOptional.get();

            Optional<Consulta> consOptional = consultaRepository.findById(idConsulta);
            if (consOptional.isPresent()) {
                Consulta consEncontr = consOptional.get();

                List<Consulta> consultas = animEncont.getConsultas();
                consultas.add(consEncontr);
                animalRepository.save(animEncont);
                
            } else {
                throw new EntityNotFoundException("Vacina não encontrada.");
            }
        } else {
            throw new EntityNotFoundException("Animal não encontrado.");
        }
    }

    public List<Consulta> exibirConsultas(Long idAnimal) {
        Optional<Animal> animOptional = animalRepository.findById(idAnimal);

        if (animOptional.isPresent()) {
            Animal animal = animOptional.get();
            return animal.getConsultas();
            
        } else {
            return Collections.emptyList();
        }
    }

    public void removerConsultaDaLista(Long idAnimal, Long idConsulta) {
        Optional<Animal> animOptional = animalRepository.findById(idAnimal);

        if (animOptional.isPresent()) {
            Animal animalEcont = animOptional.get();

            Optional<Consulta> consOptional = consultaRepository.findById(idConsulta);
            if (consOptional.isPresent()) {
                Consulta consEncont = consOptional.get();

                List<Consulta> consultas = animalEcont.getConsultas();
                consultas.remove(consEncont);
                animalRepository.save(animalEcont);
                
            } else {
                throw new EntityNotFoundException("Consulta não encontrada.");
            }
 
        } else {
            throw new EntityNotFoundException("Animal não encontrado.");
        }
    }
}
