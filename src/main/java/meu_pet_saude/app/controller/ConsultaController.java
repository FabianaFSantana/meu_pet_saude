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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import meu_pet_saude.app.constant.TipoDeConsulta;
import meu_pet_saude.app.model.Consulta;
import meu_pet_saude.app.service.AnimalService;
import meu_pet_saude.app.service.ConsultaService;

@RestController
@RequestMapping("/consulta")
public class ConsultaController {

    @PostMapping("/{animal_id}")
    public ResponseEntity<Consulta> criarRegistroDeConsulta(@PathVariable("animal_id") Long idAnimal, @RequestBody Consulta consulta) {
        return ResponseEntity.status(HttpStatus.CREATED).body(animalService.adicionarConsultaListaAnimal(idAnimal, consulta));
    }

    @GetMapping("/{consulta_id}")
    public ResponseEntity<Consulta> buscarConsultaPeloId(@PathVariable("consulta_id") Long idConsulta) {
        return ResponseEntity.status(HttpStatus.OK).body(consultaService.buscarConsultaPeloId(idConsulta));
    }

    @GetMapping("/periodo")
    public ResponseEntity<List<Consulta>> exibirConsultasPorPeriodo(@RequestParam("dataInicial") LocalDate dataInicial, @RequestParam("dataFinal") LocalDate dataFinal) {
        return ResponseEntity.status(HttpStatus.OK).body(consultaService.buscarConsultasPorPeriodo(dataInicial, dataFinal));
    }

    @GetMapping("/tipo/{animal_id}")
    public ResponseEntity<List<Consulta>> exibirConsultasPeloTipo(@PathVariable("animal_id") Long idAnimal, @RequestParam("tipoDeConsulta") TipoDeConsulta tipoDeConsulta) {
        return ResponseEntity.status(HttpStatus.OK).body(consultaService.buscarConsultasPorTipo(idAnimal, tipoDeConsulta));
    }

    @PutMapping("/{idConsulta}")
    public ResponseEntity<Consulta> atualizarDadosDaConsulta(@PathVariable("idConsulta") Long idConsulta, @RequestBody Consulta consulta) {
        return ResponseEntity.status(HttpStatus.OK).body(consultaService.atualizarDadosDaConsulta(idConsulta, consulta));
    }

    @DeleteMapping("/{animal_id/excluir/{consulta_id}")
    public ResponseEntity<String> excluirConsulta(@PathVariable("animal_id") Long idAnimal, @PathVariable("idConsulta") Long idConsulta) {
        return ResponseEntity.status(HttpStatus.OK).body(consultaService.removerConsultaDaLista(idAnimal, idConsulta));
    }

 

    @Autowired
    private ConsultaService consultaService;

    @Autowired
    private AnimalService animalService;

   
    
}
