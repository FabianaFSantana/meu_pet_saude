package meu_pet_saude.app.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import meu_pet_saude.app.dto.TutorDTO;
import meu_pet_saude.app.model.Animal;
import meu_pet_saude.app.model.Tutor;
import meu_pet_saude.app.service.AnimalService;
import meu_pet_saude.app.service.TutorService;

@RestController
@RequestMapping("/tutor")
public class TutorController {


    @Secured({"ROLE_ADMIN", "ROLE_EXT_USER"})
    @PostMapping
    public ResponseEntity<Tutor> cadastrarTutor(@RequestBody Tutor tutor){
         return ResponseEntity.status(HttpStatus.CREATED)
        .body(tutorService.salvarTutor(tutor));
    }

    @Secured("ROLE_ADMIN")
    @GetMapping
    public ResponseEntity<List<Tutor>> exibirTutores() {
        return ResponseEntity.status(HttpStatus.OK).body(tutorService.buscarTutores());
    }

    @Secured({"ROLE_ADMIN", "ROLE_EXT_USER"})
    @GetMapping("/{id}")
    public ResponseEntity<TutorDTO> exibirTutorPeloId(@PathVariable("id") Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(tutorService.buscarTutorPeloId(id));
    }

    @Secured({"ROLE_ADMIN", "ROLE_EXT_USER"})
    @GetMapping("/exibirListaDeAnimaisTutor/{idTutor}")
    public ResponseEntity<List<Animal>> exibirListaAnimais(@PathVariable("idTutor") Long idTutor) {
        List<Animal> animais = animalService.exibirListaDeAnimaisDoTutor(idTutor);
        return ResponseEntity.status(HttpStatus.OK)
        .body(animais);
    }

    @Secured({"ROLE_ADMIN", "ROLE_EXT_USER"})
    @PutMapping("/{id}")
    public ResponseEntity<Tutor> atualizarTutorPeloId(@PathVariable("id") Long id,
    @RequestBody Tutor tutor) {
            return ResponseEntity.status(HttpStatus.OK).body(tutorService.atualizarDadosTutor(id, tutor));
    }

    @Secured({"ROLE_ADMIN", "ROLE_EXT_USER"})
    @DeleteMapping("/{id}")
    public ResponseEntity<String> excluirTutor(@PathVariable("id") Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(tutorService.excluirTutor(id));
    }


    @Autowired
    private AnimalService animalService;

    @Autowired
    private TutorService tutorService;

}
