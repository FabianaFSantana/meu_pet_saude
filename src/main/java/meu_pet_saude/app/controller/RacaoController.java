package meu_pet_saude.app.controller;

import java.time.LocalDate;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import meu_pet_saude.app.dto.RacaoDTO;
import meu_pet_saude.app.model.Racao;
import meu_pet_saude.app.service.AnimalService;
import meu_pet_saude.app.service.RacaoService;

@Secured({"ROLE_ADMIN", "ROLE_EXT_USER"})
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
    public ResponseEntity<RacaoDTO> exibirRacaoPeloId(@PathVariable("racao_id") Long id) {
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

    @PutMapping("/{racao_id}")
    public ResponseEntity<Racao> atualizarDadosDaRacao(@PathVariable("racao_id") Long id, @RequestBody Racao racao) {
        return ResponseEntity.status(HttpStatus.OK).body(racaoService.atualizarRacao(id, racao));
    }

    @DeleteMapping("/{animal_id}/excluirRacao/{id}")
    public ResponseEntity<String> excluirRacao(@PathVariable("animal_id") Long idAnimal, @PathVariable("id") Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(racaoService.removerRacao(idAnimal, id));
    } 


    @Autowired
    private RacaoService racaoService;

    @Autowired
    private AnimalService animalService;
}
