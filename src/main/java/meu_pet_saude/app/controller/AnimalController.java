package meu_pet_saude.app.controller;

import java.util.List;

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
import meu_pet_saude.app.model.CarrapatoPulga;
import meu_pet_saude.app.model.Consulta;
import meu_pet_saude.app.model.Vacina;
import meu_pet_saude.app.model.Vermifugacao;
import meu_pet_saude.app.service.AnimalService;
import meu_pet_saude.app.service.CarrapatoPulgaService;
import meu_pet_saude.app.service.ConsultaService;
import meu_pet_saude.app.service.TutorService;
import meu_pet_saude.app.service.VacinaService;
import meu_pet_saude.app.service.VermifugacaoService;

@RestController
@RequestMapping("/animal")
public class AnimalController {

    @PostMapping("/{tutor_id}")
    public ResponseEntity<Animal> cadastrarAnimal(@PathVariable("tutor_id") Long id, @RequestBody Animal animal) {
        Animal novoAnimal = tutorService.adicionarAnimalNaListaDeTutor(id, animal);
        return ResponseEntity.status(HttpStatus.CREATED).body(novoAnimal);
    }

    @PostMapping("/{idAnimal}/adicionarVermifugoListaAnimal/{idVerm}")
    public ResponseEntity<String> adicionarVermigudoLista(@PathVariable("idAnimal") Long idAnimal,
    @PathVariable("idVerm") Long idVerm) {
        vermifugacaoService.adicionarVermifugacao(idAnimal, idVerm);
        return ResponseEntity.status(HttpStatus.OK)
        .body("Vermifugo adicionado à lista do animal.");
    }

    @PostMapping("/{idAnimal}/adicionarCarrapListaAnimal/{idCarrap}")
    public ResponseEntity<String> adicionarCarrapListaAnimal(@PathVariable("idAnimal") Long idAnimal,
    @PathVariable("idCarrap") Long idCarrap) {
        carrapatoPulgaService.adicionarCarrapaticidaNaLista(idAnimal, idCarrap);
        return ResponseEntity.status(HttpStatus.OK)
        .body("Carrapaticida adicionado à Lista.");
    }

    @PostMapping("/{idAnimal}/adicionarConsultaListaAnimal/{idConsulta}")
    public ResponseEntity<String> adicionarConsultaNaLista(@PathVariable("idAnimal") Long idAnimal,
    @PathVariable("idConsulta") Long idConsulta) {
        consultaService.adicionarConsultaNaLista(idAnimal, idConsulta);
        return ResponseEntity.status(HttpStatus.OK)
        .body("Consulta adicionada à lista.");
    }

    @GetMapping
    public ResponseEntity<List<Animal>> exibirListaDeAnimais() {
        return ResponseEntity.status(HttpStatus.OK).body(animalService.exibirListaDeAnimais());
    }

    @GetMapping("/{animal_id}")
    public ResponseEntity<Animal> buscarAnimalPeloId(@PathVariable("animal_id") Long idAnimal) {
        return ResponseEntity.status(HttpStatus.OK).body(animalService.buscarAnimalPeloId(idAnimal));
    }

    @GetMapping("/exibirListaDeVacinas/{animal_id}")
    public ResponseEntity<List<Vacina>> exibirListaDeVacinas(@PathVariable("animal_id") Long idAnimal) {
        return ResponseEntity.status(HttpStatus.OK).body(vacinaService.exibirListaDeVacinas(idAnimal));
    }

    @GetMapping("/exibirListaDeVermifugos/{idAnimal}")
    public ResponseEntity<List<Vermifugacao>> exibirListaDeVermifugos(@PathVariable("idAnimal") Long idAnimal) {
        List<Vermifugacao> vermifugos = vermifugacaoService.exibirListaDeVermifugosDoAnimal(idAnimal);
        return ResponseEntity.status(HttpStatus.OK).body(vermifugos);
    }

    @GetMapping("/exibirListaDeCarrapaticidas/{idAnimal}")
    public ResponseEntity<List<CarrapatoPulga>> exibirListaDeCarrapaticidas(@PathVariable("idAnimal") Long idAnimal){
        List<CarrapatoPulga> carrapaticidas = carrapatoPulgaService.exibirListaCarrapaticidasDoAnimal(idAnimal);
        return ResponseEntity.status(HttpStatus.OK).body(carrapaticidas);
    }

    @GetMapping("/exibirListaDeConsultas/{idAnimal}")
    public ResponseEntity<List<Consulta>> exibirListaConsultas(@PathVariable("idAnimal") Long idAnimal){
        List<Consulta> consultas = consultaService.exibirConsultas(idAnimal);
        return ResponseEntity.status(HttpStatus.OK).body(consultas);
    }

    @PutMapping("/{animal_id}")
    public ResponseEntity<Animal> atualizarDadosDoAnimal(@PathVariable("animal_id") Long idAnimal, @RequestBody Animal animal){
        return ResponseEntity.status(HttpStatus.OK).body(animalService.atualizarDadosDoAnimal(idAnimal, animal));
    }

    @DeleteMapping("/{tutor_id}/excluirAnimal/{animal_id}")
    public ResponseEntity<String> excluirCadastroAnimal(@PathVariable("tutor_id") Long id, @PathVariable("animal_id") Long idAnimal) {
        return ResponseEntity.status(HttpStatus.OK).body(animalService.excluirAnimal(id, idAnimal));
    }

    @DeleteMapping("/{idAnimal}/removerVermifugoDaLista/{idVerm}")
    public ResponseEntity<String> removerVermifugoDaLista(@PathVariable("idAnimal") Long idAnimal,
    @PathVariable("idVerm") Long idVerm) {
        vermifugacaoService.removerVermifugoDaLista(idAnimal, idVerm);
        return ResponseEntity.status(HttpStatus.OK)
        .body("Vermífugo removido com sucesso.");
    }

    @DeleteMapping("/{idAnimal}/removerCarrapDaLista/{idCarrap}")
    public ResponseEntity<String> removerCarrapDaLita(@PathVariable("idAnimal") Long idAnimal,
    @PathVariable("idCarrap") Long idCarrap) {
        carrapatoPulgaService.removerCarrapaticidaDaLista(idAnimal, idCarrap);
        return ResponseEntity.status(HttpStatus.OK)
        .body("Carrapaticida removido com sucesso da lista.");
    }

    @DeleteMapping("/{idAnimal}/removerConsultaListaAnimal/{idConsulta}")
    public ResponseEntity<String> removerConsultaLista(@PathVariable("idAnimal") Long idAnimal,
    @PathVariable("idConsulta") Long idConsulta) {
        consultaService.removerConsultaDaLista(idAnimal, idConsulta);
        return ResponseEntity.status(HttpStatus.OK)
        .body("Consulta removida da lista do animal.");
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
    private CarrapatoPulgaService carrapatoPulgaService;

    @Autowired
    private ConsultaService consultaService;


    
}
