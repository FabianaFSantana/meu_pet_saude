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

import meu_pet_saude.app.model.Vacina;
import meu_pet_saude.app.repository.VacinaRepository;

@RestController
@RequestMapping("/vacina")
public class VacinaController {

    @Autowired
    private VacinaRepository vacinaRepository;

    @PostMapping
    public ResponseEntity<Vacina> cadastrarVacina(@RequestBody Vacina vacina) {
        return ResponseEntity.status(HttpStatus.CREATED) 
        .body(vacinaRepository.save(vacina));
    }

    @GetMapping
    public ResponseEntity<List<Vacina>> exibirVacinas() {
        return ResponseEntity.status(HttpStatus.OK)
        .body(vacinaRepository.findAll());
    }

    @GetMapping("/{idVacina}")
    public ResponseEntity<Optional<Vacina>> exibirVacinaPeloId(@PathVariable("idVacina") Long idVacina) {
        return ResponseEntity.status(HttpStatus.OK)
        .body(vacinaRepository.findById(idVacina));
    }

    @PutMapping("/{idVacina}")
    public ResponseEntity<Vacina> atualizarDadosVacina(@PathVariable("idVacina") Long idVacina,
    @RequestBody Vacina vacina) {
        Optional<Vacina> vacOptional = vacinaRepository.findById(idVacina);

        if (vacOptional.isPresent()) {
            Vacina vacEncontrada = vacOptional.get();

            vacEncontrada.setNomeVacina(vacina.getNomeVacina());
            vacEncontrada.setDataDaUltimaDose(vacina.getDataDaUltimaDose());
            vacEncontrada.setDataDaProximaDose(vacina.getDataDaProximaDose());
            vacEncontrada.setNomeDaClinica(vacina.getNomeDaClinica());
            vacEncontrada.setNomeVeterinario(vacina.getNomeVeterinario());

            return ResponseEntity.status(HttpStatus.OK)
            .body(vacinaRepository.save(vacEncontrada));
            
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{idVacina}")
    public ResponseEntity<String> excluirVacina(@PathVariable("idVacina") Long idVacina) {
        vacinaRepository.deleteById(idVacina);
        return ResponseEntity.status(HttpStatus.OK)
        .body("Vacina excluída do banco de dados.");
    }
    
}
