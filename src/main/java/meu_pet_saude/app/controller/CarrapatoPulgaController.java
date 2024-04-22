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

import meu_pet_saude.app.model.CarrapatoPulga;
import meu_pet_saude.app.repository.CarrapatoPulgaRepository;
import meu_pet_saude.app.service.CarrapatoPulgaService;

@RestController
@RequestMapping("/carrapatoPulga")
public class CarrapatoPulgaController {

    @Autowired
    private CarrapatoPulgaRepository carrapatoPulgaRepository;

    @Autowired
    private CarrapatoPulgaService carrapatoPulgaService;


    @PostMapping
    public ResponseEntity<CarrapatoPulga> cadastrarDosagem(@RequestBody CarrapatoPulga carrapatoPulga) {
        return ResponseEntity.status(HttpStatus.CREATED)
        .body(carrapatoPulgaRepository.save(carrapatoPulga));
    }

    @PostMapping("/{idTutor}/enviarLembreteDeCarrapaticidaPorEmail/{proximaDose}")
    public ResponseEntity<String> enviarLembreteCarrapaticidaEmail(@PathVariable("idTutor") Long idTutor,
    @PathVariable("proximaDose") LocalDate proximaDose) {

        carrapatoPulgaService.exibirListaDeCarrapaticidasProximaDose(idTutor, proximaDose);
        return ResponseEntity.status(HttpStatus.OK)
        .body("Lembrete de carrapaticida enviado com sucesso.");
    }

    @GetMapping
    public ResponseEntity<List<CarrapatoPulga>> listarDosagensDadas() {
        return ResponseEntity.status(HttpStatus.OK)
        .body(carrapatoPulgaRepository.findAll());
    }

    @GetMapping("/{idCarrap}")
    public ResponseEntity<Optional<CarrapatoPulga>> localizarDosagemPeloId(@PathVariable("idCarrap") Long idCarrap) {
        return ResponseEntity.status(HttpStatus.OK)
        .body(carrapatoPulgaRepository.findById(idCarrap));
    }

    @GetMapping("/data/{data}")
    public ResponseEntity<CarrapatoPulga> buscarDosagemPorData(@PathVariable("data") LocalDate data) {
       Optional<CarrapatoPulga> carrapOptional = carrapatoPulgaRepository.findByDosagemData(data);

       if (carrapOptional.isPresent()) {
        CarrapatoPulga carrap = carrapOptional.get();
        return ResponseEntity.status(HttpStatus.OK)
        .body(carrap);
        
       } else {
        return ResponseEntity.notFound().build();
       }
    }

    @PutMapping("/{idCarrap}")
    public ResponseEntity<CarrapatoPulga> atualizarDadosMedicacao(@PathVariable("idCarrap") Long idCarrap,
    @RequestBody CarrapatoPulga carrapatoPulga) {
        Optional<CarrapatoPulga> carrapOptional = carrapatoPulgaRepository.findById(idCarrap);

        if (carrapOptional.isPresent()) {
            CarrapatoPulga carrapEncontrado = carrapOptional.get();

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
    }
    
}
