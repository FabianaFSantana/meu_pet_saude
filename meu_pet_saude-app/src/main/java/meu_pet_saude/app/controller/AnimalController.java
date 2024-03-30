package meu_pet_saude.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
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

    
    
}
