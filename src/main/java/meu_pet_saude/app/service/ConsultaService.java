package meu_pet_saude.app.service;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.persistence.EntityNotFoundException;
import meu_pet_saude.app.constant.TipoDeConsulta;
import meu_pet_saude.app.model.Animal;
import meu_pet_saude.app.model.Consulta;
import meu_pet_saude.app.repository.AnimalRepository;
import meu_pet_saude.app.repository.ConsultaRepository;

@Service
public class ConsultaService {

    public Consulta buscarConsultaPeloId(Long idConsulta) {
        Optional<Consulta> consultaOptional = consultaRepository.findById(idConsulta);

        if (consultaOptional.isPresent()) {
            return consultaOptional.get();
        }
        return null;
    }
   
    public List<Consulta> exibirConsultas(Long idAnimal) {
        Optional<Animal> animOptional = animalRepository.findById(idAnimal);

        if (animOptional.isPresent()) {
            Animal animal = animOptional.get();
            return animal.getConsultas();
            
        } else {
            return Collections.emptyList();
        }
    }

    public List<Consulta> buscarConsultasPorPeriodo(LocalDate dataInicial, LocalDate dataFinal) {
        List<Consulta> consultas = consultaRepository.findByDataDaConsultaBetween(dataInicial, dataFinal);
        return consultas;
    }

    public List<Consulta> buscarConsultasPorTipo(Long idAnimal, TipoDeConsulta tipoDeConsulta) {
        
        Optional<Animal> animalOptional = animalRepository.findById(idAnimal);

        if (animalOptional.isPresent()) {
            Animal animal = animalOptional.get();
            List<Consulta> consultas = animal.getConsultas();

            return consultas.stream().filter(consulta -> consulta.getTipoDeConsulta().equals(tipoDeConsulta)).collect(Collectors.toList());
        }
        return Collections.emptyList();
    }

    public Consulta atualizarDadosDaConsulta(Long id, Consulta consulta) {
        Optional<Consulta> consultaOptional = consultaRepository.findById(id);

        if (consultaOptional.isPresent()) {
            Consulta consultaEncont = consultaOptional.get();

            consultaEncont.setNomeDaClinica(consulta.getNomeDaClinica());
            consultaEncont.setVeterinario(consulta.getVeterinario());
            consultaEncont.setDataDaConsulta(consulta.getDataDaConsulta());
            consultaEncont.setTipoDeConsulta(consulta.getTipoDeConsulta());
            consultaEncont.setSintomas(consulta.getSintomas());
            consultaEncont.setDiagnostico(consulta.getDiagnostico());
            consultaEncont.setMedicacao(consulta.getMedicacao());
            consultaEncont.setPosologia(consulta.getPosologia());

            return consultaRepository.save(consultaEncont);
        }
        return null;
    }

    public String removerConsultaDaLista(Long idAnimal, Long idConsulta) {
        Animal animal = animalRepository.findById(idConsulta).orElse(null);
        Consulta consulta = consultaRepository.findById(idConsulta).orElse(null);

        if (animal == null) {
            return "Animal não encontrado.";
        }

        if (consulta == null) {
            return "Consulta não encontrada.";
        }

        List<Consulta> consultas = animal.getConsultas();
        if (consultas.remove(consulta)) {
            animalRepository.save(animal);
            consultaRepository.deleteById(idConsulta);
            return "Consulta removida com sucessol.";
        }
        return "Consulta não encontrada na lista do animal.";
    }

    @Autowired
    private ConsultaRepository consultaRepository;

    @Autowired
    private AnimalRepository animalRepository;
}
