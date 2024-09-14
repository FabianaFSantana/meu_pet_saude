package meu_pet_saude.app.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jakarta.persistence.EntityNotFoundException;
import meu_pet_saude.app.model.Animal;
import meu_pet_saude.app.model.Tutor;
import meu_pet_saude.app.model.Vermifugacao;
import meu_pet_saude.app.repository.AnimalRepository;
import meu_pet_saude.app.repository.TutorRepository;
import meu_pet_saude.app.repository.VermifugacaoRepository;

@Service
public class VermifugacaoService {


    public List<Vermifugacao> exibirListaDeVermifugosDoAnimal(Long idAnimal) {
        
        Optional<Animal> animOptional = animalRepository.findById(idAnimal);
        if (animOptional.isPresent()) {
            Animal animalEncont = animOptional.get();

            return animalEncont.getVermifugos();
        } else {
            return Collections.emptyList();
        }
    }

    public List<Vermifugacao> buscarVermifugos() {
        return vermifugacaoRepository.findAll();
    }

    public Vermifugacao buscarVermifugacaoPorId(Long idVerm) {
        Optional<Vermifugacao> vermOptional = vermifugacaoRepository.findById(idVerm);

        if (vermOptional.isPresent()) {
            Vermifugacao vermifugo = vermOptional.get();
            return vermifugo;
        }
        return null;
    }

    public Vermifugacao atualizarDadosVermifufo(Long idVerm, Vermifugacao vermifugacao) {
        Optional<Vermifugacao> vermOptional = vermifugacaoRepository.findById(idVerm);

        if (vermOptional.isPresent()) {
            Vermifugacao vermEncontrada = vermOptional.get();

            vermEncontrada.setNomeMedic(vermifugacao.getNomeMedic());
            vermEncontrada.setPeso(vermifugacao.getPeso());
            vermEncontrada.setDosagem(vermifugacao.getDosagem());
            vermEncontrada.setData(vermifugacao.getData());
            vermEncontrada.setProximaDose(vermifugacao.getProximaDose());

            return vermifugacaoRepository.save(vermEncontrada);
        }
        return null;

    }
    
    @Transactional
    public String removerVermifugo(Long idAnimal, Long idVerm) {
            Animal animal = animalRepository.findById(idAnimal).orElse(null);
            Vermifugacao vermifugo = vermifugacaoRepository.findById(idVerm).orElse(null);

            if (animal == null) {
                return "Animal não encontrado!";
            }
            
            if (vermifugo == null) {
                return "Vermífugo não encontrado!";
            }

            List<Vermifugacao> vermifugos = animal.getVermifugos();
            if (vermifugos.remove(vermifugo)) {
                animalRepository.save(animal);
                vermifugacaoRepository.deleteById(idVerm);
                return "Vermífugo removido com sucesso!";
            }
            return "Vermífugo não encontrado na lista do animal!";
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

    @Autowired
    private AnimalRepository animalRepository;

    @Autowired
    private VermifugacaoRepository vermifugacaoRepository;

    @Autowired
    private TutorRepository tutorRepository;

    @Autowired
    private EmailService emailService;
}
