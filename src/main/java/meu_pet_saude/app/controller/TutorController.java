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
import meu_pet_saude.app.model.Endereco;
import meu_pet_saude.app.model.Tutor;
import meu_pet_saude.app.repository.TutorRepository;
import meu_pet_saude.app.service.AnimalService;
import meu_pet_saude.app.service.ViaCepEnderecoService;

@RestController
@RequestMapping("/tutor")
public class TutorController {

    @Autowired
    private TutorRepository tutorRepository;

    @Autowired
    private ViaCepEnderecoService viaCepEnderecoService;

    @Autowired
    private AnimalService animalService;

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

    @PostMapping("/{idTutor}/adicionarAnimalNaLista/{idAnimal}")
    public ResponseEntity<String> adicionarAnimal(@PathVariable("idTutor") Long idTutor,
    @PathVariable("idAnimal") Long idAnimal) {
        animalService.adicionarAnimalNaListaDeAnimaisDeTutor(idTutor, idAnimal);
        return ResponseEntity.status(HttpStatus.OK)
        .body("Animal adiconado à lista do tutor.");
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

    @GetMapping("/exibirListaDeAnimaisTutor/{idTutor}")
    public ResponseEntity<List<Animal>> exibirListaAnimais(@PathVariable("idTutor") Long idTutor) {
        List<Animal> animais = animalService.exibirListaDeAnimaisDoTutor(idTutor);
        return ResponseEntity.status(HttpStatus.OK)
        .body(animais);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Tutor> atualizarTutorPeloId(@PathVariable("id") Long id,
    @RequestBody Tutor tutor) {
        Optional<Tutor> tutorOptional = tutorRepository.findById(id);

        if (tutorOptional.isPresent()) {
            Tutor tutorEncont = tutorOptional.get();

            tutorEncont.setNome(tutor.getNome());
            tutorEncont.setDataDeNascimento(tutor.getDataDeNascimento());
            tutorEncont.setTelefone(tutor.getTelefone());
            tutorEncont.setEmail(tutor.getEmail());
            tutorEncont.getEndereco().setCep(tutor.getEndereco().getCep());
            tutorEncont.getEndereco().setEndereco(tutor.getEndereco().getEndereco());
            tutorEncont.getEndereco().setNumero(tutor.getEndereco().getNumero());
            tutorEncont.getEndereco().setComplemento(tutor.getEndereco().getComplemento());
            tutorEncont.getEndereco().setBairro(tutor.getEndereco().getBairro());
            tutorEncont.getEndereco().setCidade(tutor.getEndereco().getCidade());
            tutorEncont.getEndereco().setEstado(tutor.getEndereco().getEstado());
            tutorEncont.getEndereco().setPais(tutor.getEndereco().getPais());

            return ResponseEntity.status(HttpStatus.OK)
            .body(tutorRepository.save(tutorEncont));
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> excluirTutor(@PathVariable("id") Long id) {
        tutorRepository.deleteById(id);
        return ResponseEntity.status(HttpStatus.OK)
        .body("Tutor exluído com sucesso!");
    }

    @DeleteMapping("/{idTutor}/removerAnimalDaLista/{idAnimal}")
    public ResponseEntity<String> removerAnimalDaLista(@PathVariable("idTutor") Long idTutor,
    @PathVariable("idAnimal") Long idAnimal) {
        animalService.removerAnimalDaLista(idTutor, idAnimal);
        return ResponseEntity.status(HttpStatus.OK)
        .body("Animal removido com sucesso da lista.");
    }

}
