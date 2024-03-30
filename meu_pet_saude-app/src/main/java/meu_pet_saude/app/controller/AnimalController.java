package meu_pet_saude.app.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import meu_pet_saude.app.model.Animal;
import meu_pet_saude.app.repository.AnimalRepository;

@RestController
@RequestMapping("/animal")
public class AnimalController {

    @Autowired
    private AnimalRepository animalRepository;

    @PostMapping
    public ResponseEntity<Animal> cadastrarAnimal(@RequestBody Animal animal) {
        return ResponseEntity.status(HttpStatus.CREATED)
        .body(animalRepository.save(animal));
    }

    @GetMapping
    public ResponseEntity<List<Animal>> exibirListaDeAnimais() {
        return ResponseEntity.status(HttpStatus.OK)
        .body(animalRepository.findAll());
    }

    @GetMapping("/{idAnimal}")
    public ResponseEntity<Optional<Animal>> buscarAnimalPeloId(@PathVariable("idAnimal") Long idAnimal) {
        return ResponseEntity.status(HttpStatus.OK)
        .body(animalRepository.findById(idAnimal));
    }

    @PutMapping("/{idAnimal}")
    public ResponseEntity<Animal> atualizarDadosDoAnimal(@PathVariable("idAnimal") Long idAnimal, 
    @RequestBody Animal animal){
        Optional<Animal> animOptional = animalRepository.findById(idAnimal);

        if (animOptional.isPresent()) {
            Animal animalEncont = animOptional.get();

            animalEncont.setNome(animal.getNome());
            animalEncont.setEspecie(animal.getEspecie());
            animalEncont.setRaca(animal.getRaca());
            animalEncont.setDataDeNascimento(animal.getDataDeNascimento());
            animalEncont.setPeso(animal.getPeso());
            animalEncont.setCorDoPelo(animal.getCorDoPelo());

            return ResponseEntity.status(HttpStatus.OK)
            .body(animalRepository.save(animalEncont));
            
        } else {
            return ResponseEntity.notFound().build();
        }

    }

    @DeleteMapping("/{idAnimal}")
    public ResponseEntity<String> excluirCadastroAnimal(@PathVariable("idAnimal") Long idAnimal) {
        animalRepository.deleteById(idAnimal);
        return ResponseEntity.status(HttpStatus.OK)
        .body("Cadastro do animal excluído com sucesso!");
    }


    
}
