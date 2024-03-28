package meu_pet_saude.app.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import meu_pet_saude.app.model.Endereco;
import meu_pet_saude.app.model.Tutor;
import meu_pet_saude.app.repository.TutorRepository;
import meu_pet_saude.app.service.ViaCepEnderecoService;

@RestController
@RequestMapping("/tutor")
public class TutorController {

    @Autowired
    private TutorRepository tutorRepository;

    @Autowired
    private ViaCepEnderecoService viaCepEnderecoService;

    @PostMapping
    public ResponseEntity<Tutor> cadastrarTutor(@RequestBody Tutor tutor){
         return ResponseEntity.status(HttpStatus.CREATED)
        .body(tutorRepository.save(tutor));
    }

    @PostMapping("/cadastrarEndereco/{id}")
    public ResponseEntity<String> atualizarEnderecoDoTutor(@PathVariable("id") Long id, @RequestBody Endereco endereco) {
        viaCepEnderecoService.buscarEnderecoPorCep(id, endereco.getCep());
        return ResponseEntity.status(HttpStatus.OK)
        .body("Endereço cadastrado!");
       
    }

    @GetMapping
    public ResponseEntity<List<Tutor>> exibirTutores() {
        return ResponseEntity.status(HttpStatus.OK)
        .body(tutorRepository.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<Tutor>> buscarTutorPeloId(@PathVariable("id") Long id) {
        return ResponseEntity.status(HttpStatus.OK)
        .body(tutorRepository.findById(id));
    }
}
