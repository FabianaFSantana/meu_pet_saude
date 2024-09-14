package meu_pet_saude.app.controller;

import java.time.LocalDate;
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
import meu_pet_saude.app.model.Carrapaticida;
import meu_pet_saude.app.service.AnimalService;
import meu_pet_saude.app.service.CarrapaticidaService;

@RestController
@RequestMapping("/carrapatoPulga")
public class CarrapaticidaController {

    @PostMapping("/{animal_id}")
    public ResponseEntity<Carrapaticida> cadastrarDosagem(@PathVariable("animal_id") Long idAnimal, @RequestBody Carrapaticida carrapatoPulga) {
        return ResponseEntity.status(HttpStatus.CREATED).body(animalService.adicionarCarrapaticidaListaAnimal(idAnimal, carrapatoPulga));
    }

    @GetMapping("/{carrap_id}")
    public ResponseEntity<Carrapaticida> exibirCarrapaticidaPeloId(@PathVariable("carrap_id") Long idCarrap) {
        return ResponseEntity.status(HttpStatus.OK).body(carrapaticidaService.buscarCarrapaticidaPeloId(idCarrap));
    }



/*     @PostMapping("/{idTutor}/enviarLembreteDeCarrapaticidaPorEmail/{proximaDose}")
    public ResponseEntity<String> enviarLembreteCarrapaticidaEmail(@PathVariable("idTutor") Long idTutor,
    @PathVariable("proximaDose") LocalDate proximaDose) {

        carrapatoPulgaService.exibirListaDeCarrapaticidasProximaDose(idTutor, proximaDose);
        return ResponseEntity.status(HttpStatus.OK)
        .body("Lembrete de carrapaticida enviado com sucesso.");
    }

    @GetMapping
    public ResponseEntity<List<Carrapaticida>> listarDosagensDadas() {
        return ResponseEntity.status(HttpStatus.OK)
        .body(carrapatoPulgaRepository.findAll());
    }

    @GetMapping("/{idCarrap}")
    public ResponseEntity<Optional<Carrapaticida>> localizarDosagemPeloId(@PathVariable("idCarrap") Long idCarrap) {
        return ResponseEntity.status(HttpStatus.OK)
        .body(carrapatoPulgaRepository.findById(idCarrap));
    }

    @GetMapping("/data/{data}")
    public ResponseEntity<Carrapaticida> buscarDosagemPorData(@PathVariable("data") LocalDate data) {
       Optional<Carrapaticida> carrapOptional = carrapatoPulgaRepository.findByDosagemData(data);

       if (carrapOptional.isPresent()) {
        Carrapaticida carrap = carrapOptional.get();
        return ResponseEntity.status(HttpStatus.OK)
        .body(carrap);
        
       } else {
        return ResponseEntity.notFound().build();
       }
    }

    @PutMapping("/{idCarrap}")
    public ResponseEntity<Carrapaticida> atualizarDadosMedicacao(@PathVariable("idCarrap") Long idCarrap,
    @RequestBody Carrapaticida carrapatoPulga) {
        Optional<Carrapaticida> carrapOptional = carrapatoPulgaRepository.findById(idCarrap);

        if (carrapOptional.isPresent()) {
            Carrapaticida carrapEncontrado = carrapOptional.get();

            carrapEncontrado.setNomeMedic(carrapatoPulga.getNomeMedic());
            carrapEncontrado.setPeso(carrapatoPulga.getPeso());
            carrapEncontrado.setDosagem(carrapatoPulga.getDosagem());
            carrapEncontrado.setData(carrapatoPulga.getData());
            carrapEncontrado.setProximaDose(carrapatoPulga.getProximaDose());

            return ResponseEntity.status(HttpStatus.OK)
            .body(carrapatoPulgaRepository.save(carrapEncontrado));
            
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{idCarrap}")
    public ResponseEntity<String> excluirMedicacao(@PathVariable("idCarrap") Long idCarrap) {
        carrapatoPulgaRepository.deleteById(idCarrap);
        return ResponseEntity.status(HttpStatus.OK)
        .body("Medicação excluída com sucesso");
    } */
    
    @Autowired
    private CarrapaticidaService carrapaticidaService;

    @Autowired
    private AnimalService animalService;


}
