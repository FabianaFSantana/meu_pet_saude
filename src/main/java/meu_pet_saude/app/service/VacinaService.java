package meu_pet_saude.app.service;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import meu_pet_saude.app.dto.VacinaDTO;
import meu_pet_saude.app.model.Animal;
import meu_pet_saude.app.model.Vacina;
import meu_pet_saude.app.repository.AnimalRepository;
import meu_pet_saude.app.repository.VacinaRepository;

@Service
public class VacinaService {

   
    public VacinaDTO buscarVacinaPorId(Long id) {
        Optional<Vacina> vacinaOptional = vacinaRepository.findById(id);

        if (vacinaOptional.isPresent()) {
            Vacina vacina = vacinaOptional.get();

            return vacina.converterVacinaDTO();
        }
        return null;
    }

    public LocalDate calcularProximaDoseVacina(Long idVacina) {
        Optional<Vacina> vacinaOptional = vacinaRepository.findById(idVacina);

        if (vacinaOptional.isPresent()) {
            Vacina vacina = vacinaOptional.get();

            LocalDate dataDose = vacina.getData();
            LocalDate proximaVacina = dataDose.plusDays(365);

            vacina.setProximaDose(proximaVacina);
            vacinaRepository.save(vacina);

            return proximaVacina;
        }
        return null;
    }

    public Vacina atualizarDadosVacina(Long id, Vacina vacina) {
        Optional<Vacina> vacOptional = vacinaRepository.findById(id);

        if (vacOptional.isPresent()) {
            Vacina vacEncontrada = vacOptional.get();

            vacEncontrada.setNomeVacina(vacina.getNomeVacina());
            vacEncontrada.setData(vacina.getData());
            vacEncontrada.setNomeDaClinica(vacina.getNomeDaClinica());
            vacEncontrada.setNomeVeterinario(vacina.getNomeVeterinario());

            return vacinaRepository.save(vacEncontrada);
        }
        return null;
    }

    @Transactional
    public String removerVacinaDaLista(Long idAnimal, Long idVacina) {
        Animal animal = animalRepository.findById(idAnimal).orElse(null);
        Vacina vacina = vacinaRepository.findById(idVacina).orElse(null);
    
        if (animal == null) {
            return "Animal não encontrado!";   
        }

        if (vacina == null) {
            return "Vacina não encontrada!";
        }

        List<Vacina> vacinas = animal.getVacinas();
        if (vacinas.remove(vacina)) {
            animalRepository.save(animal);
            vacinaRepository.deleteById(idVacina);
            return "Vacina removida com sucesso!";
        }
        return "Vacina não encontrada na lista do animal!";
    }
            
    @Transactional
    public List<VacinaDTO> exibirListaDeVacinas(Long idAnimal) {
      
        Animal animal = animalRepository.findById(idAnimal).orElse(null);

        if (animal == null) {
            return Collections.emptyList();
        }
        List<VacinaDTO> vacinas = animal.getVacinas().stream().map(vacina -> new VacinaDTO(vacina.getAnimal().getNome(), vacina.getNomeVacina(), vacina.getData(), vacina.getNomeVeterinario(), vacina.getProximaDose())).collect(Collectors.toList());
        return vacinas;
    }
   

    @Autowired
    private AnimalRepository animalRepository;

    @Autowired
    private VacinaRepository vacinaRepository;


}
