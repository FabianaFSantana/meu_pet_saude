package meu_pet_saude.app.controller;

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
@RequestMapping("/carrapaticida")
public class CarrapaticidaController {

    @PostMapping("/{animal_id}")
    public ResponseEntity<Carrapaticida> cadastrarDosagem(@PathVariable("animal_id") Long idAnimal, @RequestBody Carrapaticida carrapatoPulga) {
        return ResponseEntity.status(HttpStatus.CREATED).body(animalService.adicionarCarrapaticidaListaAnimal(idAnimal, carrapatoPulga));
    }

    @GetMapping("/{carrap_id}")
    public ResponseEntity<Carrapaticida> exibirCarrapaticidaPeloId(@PathVariable("carrap_id") Long idCarrap) {
        return ResponseEntity.status(HttpStatus.OK).body(carrapaticidaService.buscarCarrapaticidaPeloId(idCarrap));
    }

    @PutMapping("/{carrap_id}")
    public ResponseEntity<Carrapaticida> atualizarDadosMedicacao(@PathVariable("carrap_id") Long idCarrapaticida,@RequestBody Carrapaticida carrapatoPulga) {

        return ResponseEntity.status(HttpStatus.OK).body(carrapaticidaService.atualizarDadosCarrapaticida(idCarrapaticida, carrapatoPulga));    
    }
        
    @DeleteMapping("/{animal_id}/ecluirCarrapaticida/{carrap_id}")
    public ResponseEntity<String> excluirMedicacao(@PathVariable("animal_id") Long idAnimal, @PathVariable("carrap_id") Long idCarrapaticida) {
        return ResponseEntity.status(HttpStatus.OK).body(carrapaticidaService.excluirCarrapaticida(idAnimal, idCarrapaticida));
    }
    
    @Autowired
    private CarrapaticidaService carrapaticidaService;

    @Autowired
    private AnimalService animalService;


}
