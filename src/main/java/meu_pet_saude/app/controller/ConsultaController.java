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

import meu_pet_saude.app.model.Consulta;
import meu_pet_saude.app.repository.ConsultaRepository;

@RestController
@RequestMapping("/consulta")
public class ConsultaController {

    @Autowired
    private ConsultaRepository consultaRepository;

    @PostMapping
    public ResponseEntity<Consulta> cadastrarConsulta(@RequestBody Consulta consulta) {
        return ResponseEntity.status(HttpStatus.CREATED)
        .body(consultaRepository.save(consulta));
    }

    @GetMapping
    public ResponseEntity<List<Consulta>> exibirListaDeConsultas() {
        return ResponseEntity.status(HttpStatus.OK)
        .body(consultaRepository.findAll());
    }

    @GetMapping("/{idConsulta}")
    public ResponseEntity<Optional<Consulta>> buscarConsultaPeloId(@PathVariable("idConsulta") Long idConsulta) {
        return ResponseEntity.status(HttpStatus.OK)
        .body(consultaRepository.findById(idConsulta));
    }

    @PutMapping("/{idConsulta}")
    public ResponseEntity<Consulta> atualizarDadosDaConsulta(@PathVariable("idConsulta") Long idConsulta,
    @RequestBody Consulta consulta) {
        Optional<Consulta> consultaOptional = consultaRepository.findById(idConsulta);

        if (consultaOptional.isPresent()) {
            Consulta consultaEncont = consultaOptional.get();

            consultaEncont.setClinica(consulta.getClinica());
            consultaEncont.setVeterinario(consulta.getVeterinario());
            consultaEncont.setData(consulta.getData());
            consultaEncont.setTipoDeConsulta(consulta.getTipoDeConsulta());
            consultaEncont.setSintomas(consulta.getSintomas());
            consultaEncont.setDiagnostico(consulta.getDiagnostico());
            consultaEncont.setMedicacao(consulta.getMedicacao());
            consultaEncont.setPosologia(consulta.getPosologia());

            return ResponseEntity.status(HttpStatus.OK)
            .body(consultaRepository.save(consultaEncont));
            
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{idConsulta}")
    public ResponseEntity<String> excluirConsulta(@PathVariable("idConsulta") Long idConsulta) {
        consultaRepository.deleteById(idConsulta);
        return ResponseEntity.status(HttpStatus.OK)
        .body("Consulta excluída com sucesso!");
    }
    
}
