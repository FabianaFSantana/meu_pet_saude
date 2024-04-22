package meu_pet_saude.app.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.persistence.EntityNotFoundException;
import meu_pet_saude.app.model.Animal;
import meu_pet_saude.app.model.Tutor;
import meu_pet_saude.app.model.Vermifugacao;
import meu_pet_saude.app.repository.AnimalRepository;
import meu_pet_saude.app.repository.TutorRepository;
import meu_pet_saude.app.repository.VermifugacaoRepository;

@Service
public class VermifugacaoService {

    @Autowired
    private AnimalRepository animalRepository;

    @Autowired
    private VermifugacaoRepository vermifugacaoRepository;

    @Autowired
    private TutorRepository tutorRepository;

    @Autowired
    private EmailService emailService;
    
    public void adicionarVermifugacao(Long idAnimal, Long idVerm) {
        
        Optional<Animal> animalOptional = animalRepository.findById(idAnimal);
        if (animalOptional.isPresent()) {
            Animal animal = animalOptional.get();

            Optional<Vermifugacao> vermOptional = vermifugacaoRepository.findById(idVerm);
            if (vermOptional.isPresent()) {
                Vermifugacao vermifugo = vermOptional.get();

                List<Vermifugacao> vermifugos = animal.getVermifugos();
                vermifugos.add(vermifugo);
                animalRepository.save(animal);
                
            } else {
                throw new EntityNotFoundException("Vermifugo não encntrado.");
            }

        } else {
            throw new EntityNotFoundException("Animal não encontrado.");
        }
    }

    public List<Vermifugacao> exibirListaDeVermifugosDoAnimal(Long idAnimal) {
        
        Optional<Animal> animOptional = animalRepository.findById(idAnimal);
        if (animOptional.isPresent()) {
            Animal animalEncont = animOptional.get();

            return animalEncont.getVermifugos();
        } else {
            return Collections.emptyList();
        }

    }

    public void removerVermifugoDaLista(Long idAnimal, Long idVerm) {

        Optional<Animal> animOptional = animalRepository.findById(idAnimal);
        if (animOptional.isPresent()) {
            Animal animalEncont = animOptional.get();

            Optional<Vermifugacao> vermOptional = vermifugacaoRepository.findById(idVerm);
            if (vermOptional.isPresent()) {
                Vermifugacao vermifugo = vermOptional.get();

                List<Vermifugacao> vermifugos = animalEncont.getVermifugos();
                vermifugos.remove(vermifugo);
                animalRepository.save(animalEncont);
                
            } else {
                throw new EntityNotFoundException("Vermifugo não foi encontrado.");
            }
            
        } else {
            throw new EntityNotFoundException("Animal não foi encontrado.");
        }
    }

    public List<Vermifugacao> exibirListaDeVermifugosNaDataAtual(Long idTutor, LocalDate proximaDose) {

        Optional<Tutor> tutorOptional = tutorRepository.findById(idTutor);
        List<Vermifugacao> vermifugosHoje = new ArrayList<>();

        if (tutorOptional.isPresent()) {
            Tutor tutorEncont = tutorOptional.get();
            List<Animal> animais = tutorEncont.getAnimais();

            for (Animal animal : animais) {
                if (!animal.getVermifugos().isEmpty()) {
                    List<Vermifugacao> vermifugos = animal.getVermifugos();

                    for (Vermifugacao vermifugo : vermifugos) {
                        if (vermifugo.getProximaDose().isEqual(proximaDose)) {
                            vermifugosHoje.add(vermifugo);
                        }
                    }

                    for (Vermifugacao vermifugo : vermifugosHoje) {
                        emailService.enviarEmailDeRefocoDeVermifugacao(tutorEncont, vermifugo, animal);
                    }  
                }
            }
        }
        return vermifugosHoje;
    }
}
