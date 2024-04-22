package meu_pet_saude.app.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import jakarta.persistence.EntityNotFoundException;
import meu_pet_saude.app.model.Animal;
import meu_pet_saude.app.model.Tutor;
import meu_pet_saude.app.model.Vacina;
import meu_pet_saude.app.repository.AnimalRepository;
import meu_pet_saude.app.repository.TutorRepository;
import meu_pet_saude.app.repository.VacinaRepository;

@Service
public class VacinaService {

    @Autowired
    private AnimalRepository animalRepository;

    @Autowired
    private VacinaRepository vacinaRepository;

    @Autowired
    private TutorRepository tutorRepository;

    @Autowired
    private EmailService emailService;

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

    public List<Vacina> exibirListaDeVacinasPorDataProximaDose(Long idTutor, LocalDate dataDaProximaDose) {

        Optional<Tutor> tutorOptional = tutorRepository.findById(idTutor);
        List<Vacina> vacinasHoje = new ArrayList<>();
    
        if (tutorOptional.isPresent()) {
            Tutor tutorEncontrado = tutorOptional.get();
            List<Animal> animais = tutorEncontrado.getAnimais();
    
            for (Animal animal : animais) {
                if (!animal.getVacinas().isEmpty()) {
                    List<Vacina> vacinas = animal.getVacinas();
                    for (Vacina vacina : vacinas) {
                        if (vacina.getDataDaProximaDose().equals(dataDaProximaDose)) {
                            vacinasHoje.add(vacina);
                        }
                    }
                    for (Vacina vacina : vacinasHoje) {
                        emailService.enviarEmailDeRefocoDeVacina(tutorEncontrado, vacina, animal);
                    }
                }
            }
        }
        return vacinasHoje;
    }
}
