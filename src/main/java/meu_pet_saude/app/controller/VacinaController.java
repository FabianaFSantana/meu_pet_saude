package meu_pet_saude.app.controller;

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

import meu_pet_saude.app.dto.VacinaDTO;
import meu_pet_saude.app.model.Vacina;
import meu_pet_saude.app.service.AnimalService;
import meu_pet_saude.app.service.VacinaService;

@Secured({"ROLE_ADMIN", "ROLE_EXT_USER"})
@RestController
@RequestMapping("/vacina")
public class VacinaController {



    @PostMapping("/{animal_id}")
    public ResponseEntity<VacinaDTO> cadastrarVacina(@PathVariable("animal_id") Long idAnimal, @RequestBody Vacina vacina) {

        VacinaDTO novaVacina = animalService.adicionarVacinaNaListaDoAnimal(idAnimal, vacina);
        return ResponseEntity.status(HttpStatus.CREATED).body(novaVacina);
    }

    @GetMapping("/{vacina_id}")
    public ResponseEntity<VacinaDTO> exibirVacinaPeloId(@PathVariable("vacina_id") Long idVacina) {
        return ResponseEntity.status(HttpStatus.OK).body(vacinaService.buscarVacinaPorId(idVacina));
    }

    @PutMapping("/{vacina_id}")
    public ResponseEntity<Vacina> atualizarDadosVacina(@PathVariable("vacina_id") Long idVacina, @RequestBody Vacina vacina) {

        return ResponseEntity.status(HttpStatus.OK).body(vacinaService.atualizarDadosVacina(idVacina, vacina));
    }

    @DeleteMapping("/{animal_id}/excluirVacina/{vacina_id}")
    public ResponseEntity<String> excluirVacina(@PathVariable("animal_id") Long idAnimal, @PathVariable("vacina_id") Long idVacina) {
        return ResponseEntity.status(HttpStatus.OK).body(vacinaService.removerVacinaDaLista(idAnimal, idVacina));
    }
    

    @Autowired
    private VacinaService vacinaService;

    @Autowired 
    private AnimalService animalService;
}
