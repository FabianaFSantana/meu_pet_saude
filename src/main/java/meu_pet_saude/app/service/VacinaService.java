package meu_pet_saude.app.service;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.persistence.EntityNotFoundException;
import meu_pet_saude.app.model.Animal;
import meu_pet_saude.app.model.Vacina;
import meu_pet_saude.app.repository.AnimalRepository;
import meu_pet_saude.app.repository.VacinaRepository;

@Service
public class VacinaService {

    @Autowired
    private AnimalRepository animalRepository;

    @Autowired
    private VacinaRepository vacinaRepository;

    public void adicionarVacinaListaDeVacinasAnimal(Long idAnimal, Long idVacina) {
        Optional<Animal> animOptional = animalRepository.findById(idAnimal);
        if (animOptional.isPresent()) {
            Animal animalEncont = animOptional.get();

            Optional<Vacina> vacOptional = vacinaRepository.findById(idVacina);
            if (vacOptional.isPresent()) {
                Vacina vacina = vacOptional.get();

                List<Vacina> vacinas = animalEncont.getVacinas();
                vacinas.add(vacina);
                animalRepository.save(animalEncont);
                
            } else {
                throw new EntityNotFoundException("Vacina não encontrada.");
            }
            
        } else {
            throw new EntityNotFoundException("Animal não encontrado.");
        }
    }

    public void removerVacinaDaLista(Long idAnimal, Long idVacina) {
        Optional<Animal> animOptional = animalRepository.findById(idAnimal);
        if (animOptional.isPresent()) {
            Animal animalEncont = animOptional.get();

            Optional<Vacina> vacOptional = vacinaRepository.findById(idVacina);
            if (vacOptional.isPresent()) {
                Vacina vacina = vacOptional.get();
                
                List<Vacina> vacinas = animalEncont.getVacinas();
                vacinas.remove(vacina);
                animalRepository.save(animalEncont);
                
            }
            throw new EntityNotFoundException("Vacina não econtrada.");
            
        }
        throw new EntityNotFoundException("Animal não encontrado.");
    }

    public List<Vacina> exibirListaDeVacinas(Long idAnimal) {
        Optional<Animal> animOptional = animalRepository.findById(idAnimal);
        if (animOptional.isPresent()) {
            Animal animalEncont = animOptional.get();

            return animalEncont.getVacinas();
            
        }
        return Collections.emptyList();
    }

    public List<Vacina> exibirListaDeVacinasPorDataProximaDose(LocalDate dataDaProximaDose) {

        List<Vacina> vacinas = vacinaRepository.findAllByDataProximaDosagem(dataDaProximaDose);
        
        return vacinas;
    }
    
}
