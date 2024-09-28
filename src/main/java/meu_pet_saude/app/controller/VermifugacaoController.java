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

import meu_pet_saude.app.model.Vermifugacao;
import meu_pet_saude.app.service.AnimalService;
import meu_pet_saude.app.service.VermifugacaoService;

@RestController
@RequestMapping("/vermifugacao")
public class VermifugacaoController {

    @PostMapping("/{animal_id}")
    public ResponseEntity<Vermifugacao> cadastrarVermifugacao(@PathVariable("animal_id") Long idAnimal, @RequestBody Vermifugacao vermifugacao) {
        return ResponseEntity.status(HttpStatus.CREATED).body(animalService.adicionarVermifugoListaAnimal(idAnimal, vermifugacao));
    }

    @Secured({"ROLE_ADMIN", "ROLE_EXT_USER"})
    @GetMapping("/{verm_id}")
    public ResponseEntity<Vermifugacao> exibirVermifugacaoPeloId(@PathVariable("verm_id") Long idVerm) {
        return ResponseEntity.status(HttpStatus.OK).body(vermifugacaoService.buscarVermifugacaoPorId(idVerm));
    }

    @PutMapping("/{verm_id}")
    public ResponseEntity<Vermifugacao> atualizarDadosDaVermifugacao(@PathVariable("verm_id") Long idVerm, @RequestBody Vermifugacao vermifugacao) {
                    
        return ResponseEntity.status(HttpStatus.OK).body(vermifugacaoService.atualizarDadosVermifufo(idVerm, vermifugacao));
    } 

    @DeleteMapping("/{animal_id}/excluirvermifugo/{verm_id}")
    public ResponseEntity<String> excluirDadosDeVermifugacao(@PathVariable("animal_id") Long idAnimal, @PathVariable("verm_id") Long idVerm) {
        return ResponseEntity.status(HttpStatus.OK).body(vermifugacaoService.removerVermifugo(idAnimal, idVerm));
    }

    
    

   /* 
     
    @PostMapping("/{idTutor}/enviarLembreteDeVermifugacaoPorEmail/{proximaDose}")
    public ResponseEntity<String> enviarLembreteVermifPorEmail(@PathVariable("idTutor") Long idTutor,
    @PathVariable("proximaDose") LocalDate proximaDose) {
        
        vermifugacaoService.exibirListaDeVermifugosNaDataAtual(idTutor, proximaDose);
        return ResponseEntity.status(HttpStatus.OK)
        .body("Lembrete de vermífugo enviado com sucesso!");
    }

    */
    @Autowired
    private AnimalService animalService;

    @Autowired 
    private VermifugacaoService vermifugacaoService;

}
