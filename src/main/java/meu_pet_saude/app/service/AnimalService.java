package meu_pet_saude.app.service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import meu_pet_saude.app.model.Animal;
import meu_pet_saude.app.model.Tutor;
import meu_pet_saude.app.repository.AnimalRepository;
import meu_pet_saude.app.repository.TutorRepository;

@Service
public class AnimalService {
    
    public List<Animal> exibirListaDeAnimais() {
        return animalRepository.findAll();
    }

    public Animal buscarAnimalPeloId(Long id) {
        Optional<Animal> animOptional = animalRepository.findById(id);

        if (animOptional.isPresent()) {
            Animal animal = animOptional.get();
            return animal;
        }
        return null;
    }

    public List<Animal> exibirListaDeAnimaisDoTutor(Long idTutor) {
        Optional<Tutor> tutorOptional = tutorRepository.findById(idTutor);

        if (tutorOptional.isPresent()) {
            Tutor tutor = tutorOptional.get();
            return tutor.getAnimais();
            
        } else {
            return Collections.emptyList();
        }
    }

    public Animal atualizarDadosDoAnimal(Long id, Animal animal) {
        Optional<Animal> animalOptional = animalRepository.findById(id);

        if (animalOptional.isPresent()) {
            Animal animalEncontrado = animalOptional.get();
    
                animalEncontrado.setNome(animal.getNome());
                animalEncontrado.setEspecie(animal.getEspecie());
                animalEncontrado.setRaca(animal.getRaca());
                animalEncontrado.setGenero(animal.getGenero());
                animalEncontrado.setDataDeNascimento(animal.getDataDeNascimento());
                animalEncontrado.setPeso(animal.getPeso());
                animalEncontrado.setCorDoPelo(animal.getCorDoPelo());

                return animalRepository.save(animalEncontrado);
        }
        return null;
    }

    public String excluirAnimal(Long id) {
        animalRepository.deleteById(id);
        return "Animal removido com sucesso!";
    }

    @Autowired
    private AnimalRepository animalRepository;

    @Autowired
    private TutorRepository tutorRepository;

}
