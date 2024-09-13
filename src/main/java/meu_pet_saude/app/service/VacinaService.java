package meu_pet_saude.app.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import meu_pet_saude.app.model.Animal;
import meu_pet_saude.app.model.Tutor;
import meu_pet_saude.app.model.Vacina;
import meu_pet_saude.app.repository.AnimalRepository;
import meu_pet_saude.app.repository.TutorRepository;
import meu_pet_saude.app.repository.VacinaRepository;

@Service
public class VacinaService {

    public List<Vacina> exibirVacinasCadastradas() {
        return vacinaRepository.findAll();
    }

    public Vacina buscarVacinaPorId(Long id) {
        Optional<Vacina> vacinaOptional = vacinaRepository.findById(id);

        if (vacinaOptional.isPresent()) {
            Vacina vacina = vacinaOptional.get();

            return vacina;
        }
        return null;
    }

    public Vacina atualizarDadosVacina(Long id, Vacina vacina) {
        Optional<Vacina> vacOptional = vacinaRepository.findById(id);

        if (vacOptional.isPresent()) {
            Vacina vacEncontrada = vacOptional.get();

            vacEncontrada.setNomeVacina(vacina.getNomeVacina());
            vacEncontrada.setDataDaUltimaDose(vacina.getDataDaUltimaDose());
            vacEncontrada.setDataDaProximaDose(vacina.getDataDaProximaDose());
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
    public List<Vacina> exibirListaDeVacinas(Long idAnimal) {
      
        Animal animal = animalRepository.findById(idAnimal).orElse(null);

        if (animal == null) {
            return Collections.emptyList();
        }
        List<Vacina> vacinas = animal.getVacinas();
        return vacinas;
    }

    public List<Vacina> enviarLemreteDeDoseDeReforcoVacinaPorEmail(Long idTutor, LocalDate dataDaProximaDose) {

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

    @Autowired
    private AnimalRepository animalRepository;

    @Autowired
    private VacinaRepository vacinaRepository;

    @Autowired
    private TutorRepository tutorRepository;

    @Autowired
    private EmailService emailService;
}
