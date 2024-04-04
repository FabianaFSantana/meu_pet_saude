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
import meu_pet_saude.app.model.CarrapatoPulga;
import meu_pet_saude.app.model.Consulta;
import meu_pet_saude.app.model.Vacina;
import meu_pet_saude.app.model.Vermifugacao;
import meu_pet_saude.app.repository.AnimalRepository;
import meu_pet_saude.app.service.CarrapatoPulgaService;
import meu_pet_saude.app.service.ConsultaService;
import meu_pet_saude.app.service.VacinaService;
import meu_pet_saude.app.service.VermifugacaoService;

@RestController
@RequestMapping("/animal")
public class AnimalController {

    @Autowired
    private AnimalRepository animalRepository;

    @Autowired
    private VacinaService vacinaService;

    @Autowired
    private VermifugacaoService vermifugacaoService;

    @Autowired
    private CarrapatoPulgaService carrapatoPulgaService;

    @Autowired
    private ConsultaService consultaService;

    @PostMapping
    public ResponseEntity<Animal> cadastrarAnimal(@RequestBody Animal animal) {
        return ResponseEntity.status(HttpStatus.CREATED)
        .body(animalRepository.save(animal));
    }

    @PostMapping("/{idAnimal}/adicionarVacinaListaAnimal/{idVacina}")
    public ResponseEntity<String> adicionarVacinaLista(@PathVariable("idAnimal") Long idAnimal,
    @PathVariable("idVacina") Long idVacina) {
        vacinaService.adicionarVacinaListaDeVacinasAnimal(idAnimal, idVacina);
        return ResponseEntity.status(HttpStatus.OK)
        .body("Vacina adicionada à lista de vacinas do Animal");
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
        return ResponseEntity.status(HttpStatus.OK)
        .body(animalRepository.findAll());
    }

    @GetMapping("/{idAnimal}")
    public ResponseEntity<Optional<Animal>> buscarAnimalPeloId(@PathVariable("idAnimal") Long idAnimal) {
        return ResponseEntity.status(HttpStatus.OK)
        .body(animalRepository.findById(idAnimal));
    }

    @GetMapping("/exibirListaDeVacinas/{idAnimal}")
    public ResponseEntity<List<Vacina>> exibirListaDeVacinas(@PathVariable("idAnimal") Long idAnimal) {
        List<Vacina> vacinas = vacinaService.exibirListaDeVacinas(idAnimal);
        return ResponseEntity.status(HttpStatus.OK).body(vacinas);
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

    @PutMapping("/{idAnimal}")
    public ResponseEntity<Animal> atualizarDadosDoAnimal(@PathVariable("idAnimal") Long idAnimal, 
    @RequestBody Animal animal){
        Optional<Animal> animOptional = animalRepository.findById(idAnimal);

        if (animOptional.isPresent()) {
            Animal animalEncont = animOptional.get();

            animalEncont.setNome(animal.getNome());
            animalEncont.setEspecie(animal.getEspecie());
            animalEncont.setRaca(animal.getRaca());
            animalEncont.setGenero(animal.getGenero());
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

    @DeleteMapping("/{idAnimal}/removerVacinaDaLista/{idVacina}")
    public ResponseEntity<String> removerVacinaDaLista(@PathVariable("idAnimal") Long idAnimal,
    @PathVariable("idVacina") Long idVacina) {
        vacinaService.removerVacinaDaLista(idAnimal, idVacina);
        return ResponseEntity.status(HttpStatus.OK)
        .body("Vacina removida da lista de vacinas.");
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


    
}
