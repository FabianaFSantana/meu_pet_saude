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
import org.springframework.web.service.annotation.GetExchange;

import meu_pet_saude.app.model.Vermifugacao;
import meu_pet_saude.app.repository.VermifugacaoRepository;

@RestController
@RequestMapping("/vermifugacao")
public class VermifugacaoController {

    @Autowired
    private VermifugacaoRepository vermifugacaoRepository;

    @PostMapping
    public ResponseEntity<Vermifugacao> cadastrarVermifugacao(@RequestBody Vermifugacao vermifugacao) {
        return ResponseEntity.status(HttpStatus.CREATED)
        .body(vermifugacaoRepository.save(vermifugacao));
    }

    @GetMapping
    public ResponseEntity<List<Vermifugacao>> exibirListaDeVermifugacao() {
        return ResponseEntity.status(HttpStatus.OK)
        .body(vermifugacaoRepository.findAll());
    }

    @GetMapping("/{idVerm}")
    public ResponseEntity<Optional<Vermifugacao>> exibirVermifugacaoPeloId(@PathVariable("idVerm") Long idVerm) {
        return ResponseEntity.status(HttpStatus.OK)
        .body(vermifugacaoRepository.findById(idVerm));
    }
    
    @PutMapping("/{idVerm}")
    public ResponseEntity<Vermifugacao> atualizarDadosDaVermifugacao(@PathVariable("idVerm") Long idVerm,
    @RequestBody Vermifugacao vermifugacao) {
        Optional<Vermifugacao> vermOptional = vermifugacaoRepository.findById(idVerm);

        if (vermOptional.isPresent()) {
            Vermifugacao vermEncontrada = vermOptional.get();

            vermEncontrada.setNomeMedic(vermifugacao.getNomeMedic());
            vermEncontrada.setPeso(vermifugacao.getPeso());
            vermEncontrada.setDosagem(vermifugacao.getDosagem());
            vermEncontrada.setData(vermifugacao.getData());
            vermEncontrada.setProximaDose(vermifugacao.getProximaDose());

            return ResponseEntity.status(HttpStatus.OK)
            .body(vermifugacaoRepository.save(vermEncontrada));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{idVerm}")
    public ResponseEntity<String> excluirDadosDeVermifugacao(@PathVariable("idVerm") Long idVerm) {
        vermifugacaoRepository.deleteById(idVerm);
        return ResponseEntity.status(HttpStatus.OK)
        .body("Dados excluídos com sucesso!");
    }

}
