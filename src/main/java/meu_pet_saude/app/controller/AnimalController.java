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

import meu_pet_saude.app.dto.AnimalDTO;
import meu_pet_saude.app.dto.RacaoDTO;
import meu_pet_saude.app.dto.VacinaDTO;
import meu_pet_saude.app.model.Animal;
import meu_pet_saude.app.model.Carrapaticida;
import meu_pet_saude.app.model.Consulta;
import meu_pet_saude.app.model.Vermifugacao;
import meu_pet_saude.app.service.AnimalService;
import meu_pet_saude.app.service.CarrapaticidaService;
import meu_pet_saude.app.service.ConsultaService;
import meu_pet_saude.app.service.RacaoService;
import meu_pet_saude.app.service.TutorService;
import meu_pet_saude.app.service.VacinaService;
import meu_pet_saude.app.service.VermifugacaoService;

@RestController
@RequestMapping("/animal")
public class AnimalController {

    @Secured({"ROLE_ADMIN", "ROLE_EXT_USER"})
    @PostMapping("/{tutor_id}")
    public ResponseEntity<AnimalDTO> cadastrarAnimal(@PathVariable("tutor_id") Long id, @RequestBody Animal animal) {
        AnimalDTO novoAnimal = tutorService.adicionarAnimalNaListaDeTutor(id, animal);
        return ResponseEntity.status(HttpStatus.CREATED).body(novoAnimal);
    }

    @Secured({"ROLE_ADMIN", "ROLE_EXT_USER"})
    @GetMapping
    public ResponseEntity<List<AnimalDTO>> exibirListaDeAnimaisCadastrados() {
        return ResponseEntity.status(HttpStatus.OK).body(animalService.exibirListaDeAnimais());
    }

    @Secured({"ROLE_ADMIN", "ROLE_EXT_USER"})
    @GetMapping("/{animal_id}")
    public ResponseEntity<AnimalDTO> buscarAnimalPeloId(@PathVariable("animal_id") Long idAnimal) {
        return ResponseEntity.status(HttpStatus.OK).body(animalService.buscarAnimalPeloId(idAnimal));
    }

    @GetMapping("/exibirListaDeVacinas/{animal_id}")
    public ResponseEntity<List<VacinaDTO>> exibirListaDeVacinas(@PathVariable("animal_id") Long idAnimal) {
        return ResponseEntity.status(HttpStatus.OK).body(vacinaService.exibirListaDeVacinas(idAnimal));
    }

    @GetMapping("/exibirListaDeVermifugos/{animal_id}")
    public ResponseEntity<List<Vermifugacao>> exibirListaDeVermifugos(@PathVariable("animal_id") Long idAnimal) {
        return ResponseEntity.status(HttpStatus.OK).body(vermifugacaoService.exibirListaDeVermifugosDoAnimal(idAnimal));
    }

    @GetMapping("/exibirListaDeCarrapaticidas/{animal_id}")
    public ResponseEntity<List<Carrapaticida>> exibirListaDeCarrapaticidas(@PathVariable("animal_id") Long idAnimal){
        return ResponseEntity.status(HttpStatus.OK).body(carrapaticidaService.exibirListaCarrapaticidasDoAnimal(idAnimal));
    }

    @GetMapping("/exibirListaDeConsultas/{idAnimal}")
    public ResponseEntity<List<Consulta>> exibirListaConsultas(@PathVariable("idAnimal") Long idAnimal){
        return ResponseEntity.status(HttpStatus.OK).body(consultaService.exibirConsultas(idAnimal));
    }

    @Secured({"ROLE_ADMIN", "ROLE_EXT_USER"})
    @GetMapping("/exibirListaDeRacoes/{animal_id}")
    public ResponseEntity<List<RacaoDTO>> exibirListaDeRacoesDoAnimal(@PathVariable("animal_id") Long idAnimal){
        return ResponseEntity.status(HttpStatus.OK).body(racaoService.buscarRacoesPorAnimal(idAnimal));
    }

    @Secured({"ROLE_ADMIN", "ROLE_EXT_USER"})
    @PutMapping("/{animal_id}")
    public ResponseEntity<Animal> atualizarDadosDoAnimal(@PathVariable("animal_id") Long idAnimal, @RequestBody Animal animal){
        return ResponseEntity.status(HttpStatus.OK).body(animalService.atualizarDadosDoAnimal(idAnimal, animal));
    }

    @Secured({"ROLE_ADMIN", "ROLE_EXT_USER"})
    @DeleteMapping("/{tutor_id}/excluirAnimal/{animal_id}")
    public ResponseEntity<String> excluirCadastroAnimal(@PathVariable("tutor_id") Long id, @PathVariable("animal_id") Long idAnimal) {
        return ResponseEntity.status(HttpStatus.OK).body(animalService.excluirAnimal(id, idAnimal));
    }


    @Autowired
    private TutorService tutorService;

    @Autowired
    private AnimalService animalService;

    @Autowired
    private VacinaService vacinaService;

    @Autowired
    private VermifugacaoService vermifugacaoService;

    @Autowired
    private CarrapaticidaService carrapaticidaService;

    @Autowired
    private ConsultaService consultaService;

    @Autowired
    private RacaoService racaoService;


    
}
