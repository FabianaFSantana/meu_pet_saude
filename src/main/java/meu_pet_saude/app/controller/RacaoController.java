package meu_pet_saude.app.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import meu_pet_saude.app.dto.RacaoDTO;
import meu_pet_saude.app.model.Racao;
import meu_pet_saude.app.service.AnimalService;
import meu_pet_saude.app.service.RacaoService;

@RestController
@RequestMapping("/racao")
public class RacaoController {
    
    @PostMapping("/{animal_id}")
    public ResponseEntity<Racao> cadastrarRacao(@PathVariable("animal_id") Long idAnimal, @RequestBody Racao racao) {
        Racao novaRacao = animalService.adicionarRacaoNaListaDoAnimal(idAnimal, racao);
        return ResponseEntity.status(HttpStatus.CREATED).body(novaRacao);
    }

    @GetMapping
    public ResponseEntity<List<RacaoDTO>> exibirRacoesCompradas() {
        List<RacaoDTO> racoes = racaoService.listarRacoes();
        return ResponseEntity.status(HttpStatus.OK).body(racoes);
    }

    @GetMapping("/{racao_id}")
    public ResponseEntity<Racao> exibirRacaoPeloId(@PathVariable("racao_id") Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(racaoService.buscarRacaoPeloId(id));
    }

    @GetMapping("/nomeRacao")
    public ResponseEntity<List<RacaoDTO>> buscarListaDeRacoesPeloNome(@RequestParam("nome") String nome) {
        return ResponseEntity.status(HttpStatus.OK).body(racaoService.buscarRacoesPeloNome(nome));
    }

    @GetMapping("/nomeLoja")
    public ResponseEntity<List<RacaoDTO>> exibirRacoesCompradasNaLoja(@RequestParam("loja") String loja) {
        return ResponseEntity.status(HttpStatus.OK).body(racaoService.buscarRacoesPelaLoja(loja));
    }

    @GetMapping("/dataCompra")
    public ResponseEntity<List<RacaoDTO>> exbirRacoesPorPeriodoDeCompra(@RequestParam("dataInicial") LocalDate dataInicial, @RequestParam("dataFinal") LocalDate dataFinal) {
        return ResponseEntity.status(HttpStatus.OK).body(racaoService.buscarRacoesPelaDataDeCompra(dataInicial, dataFinal));
    }

    @GetMapping("/animal/{animal_id}")
    public ResponseEntity<List<RacaoDTO>> exibirListaDeRacoesDoAnimal(@PathVariable("animal_id") Long idAnimal) {
        return ResponseEntity.status(HttpStatus.OK).body(racaoService.buscarRacoesPorAnimal(idAnimal));
    }


    @Autowired
    private RacaoService racaoService;

    @Autowired
    private AnimalService animalService;
}
