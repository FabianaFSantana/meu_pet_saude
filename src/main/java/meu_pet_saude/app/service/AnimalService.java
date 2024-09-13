package meu_pet_saude.app.service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import meu_pet_saude.app.model.Animal;
import meu_pet_saude.app.model.Tutor;
import meu_pet_saude.app.model.Vacina;
import meu_pet_saude.app.repository.AnimalRepository;
import meu_pet_saude.app.repository.TutorRepository;
import meu_pet_saude.app.repository.VacinaRepository;

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

    public Vacina adicinarVacinaNaListaDoAnimal(Long id, Vacina vacina) {
        Optional<Animal> animalOptional = animalRepository.findById(id);

        if (animalOptional.isPresent()) {
            Animal animal = animalOptional.get();

            animal.addVacina(vacina);
            vacinaRepository.save(vacina);
            animalRepository.save(animal);
            
            return vacina;
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

    @Transactional
    public String excluirAnimal(Long idTutor, Long idAnimal) {
        
        Tutor tutor = tutorRepository.findById(idTutor).orElse(null);
        Animal animal = animalRepository.findById(idAnimal).orElse(null);

        if (tutor == null) {
            return "Tutor não encontrado!";
        }

        if (animal == null) {
            return "Animal não encontrado!";
        }

        List<Animal> animais = tutor.getAnimais();
        if (animais.remove(animal)) {
            tutorRepository.save(tutor);
            animalRepository.deleteById(idAnimal);
            return "Animal removido com sucesso!";
        }
        return "Animal não encontrado na lista do tutor!";
    }

    @Autowired
    private AnimalRepository animalRepository;

    @Autowired
    private TutorRepository tutorRepository;

    @Autowired 
    private VacinaRepository vacinaRepository;

}
