package meu_pet_saude.app.service;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import meu_pet_saude.app.dto.AnimalDTO;
import meu_pet_saude.app.dto.CarrapaticidaDTO;
import meu_pet_saude.app.dto.ConsultaDTO;
import meu_pet_saude.app.dto.VacinaDTO;
import meu_pet_saude.app.dto.VermifugacaoDTO;
import meu_pet_saude.app.model.Animal;
import meu_pet_saude.app.model.Carrapaticida;
import meu_pet_saude.app.model.Consulta;
import meu_pet_saude.app.model.Racao;
import meu_pet_saude.app.model.Tutor;
import meu_pet_saude.app.model.Vacina;
import meu_pet_saude.app.model.Vermifugacao;
import meu_pet_saude.app.repository.AnimalRepository;
import meu_pet_saude.app.repository.CarrapaticidaRepository;
import meu_pet_saude.app.repository.ConsultaRepository;
import meu_pet_saude.app.repository.RacaoRepository;
import meu_pet_saude.app.repository.TutorRepository;
import meu_pet_saude.app.repository.VacinaRepository;
import meu_pet_saude.app.repository.VermifugacaoRepository;

@Service
public class AnimalService {
    
    public List<AnimalDTO> exibirListaDeAnimais() {
        return animalRepository.findAll().stream().map(animal -> new AnimalDTO(animal.getNome(), animal.getEspecie(), animal.getRaca(), animal.getGenero())).collect(Collectors.toList());
    }

    public AnimalDTO buscarAnimalPeloId(Long id) {
        Optional<Animal> animOptional = animalRepository.findById(id);

        if (animOptional.isPresent()) {
            Animal animal = animOptional.get();
            return animal.converterAnimalDTO();
        }
        return null;
    }

    public Racao adicionarRacaoNaListaDoAnimal(Long id, Racao racao) {
        Optional<Animal> animalOptional = animalRepository.findById(id);

        if (animalOptional.isPresent()) {
            Animal animal = animalOptional.get();

            racaoRepository.save(racao);
            LocalDate dataProximaCompra = racaoService.calcularConsumoDaRacao(racao.getId());
            racao.setDataProximaCompra(dataProximaCompra);
            animal.addRacao(racao);
            animalRepository.save(animal);

            return racao; 
        }
        return null;
    }

    public VacinaDTO adicionarVacinaNaListaDoAnimal(Long id, Vacina vacina) {
        Optional<Animal> animalOptional = animalRepository.findById(id);

        if (animalOptional.isPresent()) {
            Animal animal = animalOptional.get();

            vacinaRepository.save(vacina);
            LocalDate proximaDose = vacinaService.calcularProximaDoseVacina(vacina.getIdVacina());
            vacina.setProximaDose(proximaDose);

            animal.addVacina(vacina);
            animalRepository.save(animal);
            
            return vacina.converterVacinaDTO();
        }
        return null;
    }

    public VermifugacaoDTO adicionarVermifugoListaAnimal(Long idAnimal, Vermifugacao vermifugo) {
        Optional<Animal> animOptional = animalRepository.findById(idAnimal);

        if (animOptional.isPresent()) {
            Animal animal = animOptional.get();

            vermifugacaoRepository.save(vermifugo);
            LocalDate proximaDose = vermifugacaoService.calcularProximaDoseVermifugacao(vermifugo.getIdVerm());
            vermifugo.setProximaDose(proximaDose);

            animal.addVermifugo(vermifugo);
            animalRepository.save(animal);

            return vermifugo.converterVermifugacaoDTO();
        }
        return null;
    }


    public CarrapaticidaDTO adicionarCarrapaticidaListaAnimal(Long idAnimal, Carrapaticida carrapaticida) {
        Optional<Animal> animalOptional = animalRepository.findById(idAnimal);

        if (animalOptional.isPresent()) {
            Animal animal = animalOptional.get();

            carrapaticidaRepository.save(carrapaticida);
            LocalDate proximaDose = carrapaticidaService.calcularProximaDoseCarrapaticida(carrapaticida.getIdCarrapaticida());
            carrapaticida.setProximaDose(proximaDose);

            animal.addCarrapaticida(carrapaticida);
            animalRepository.save(animal);
        
            return carrapaticida.converterCarrapaticidaDTO();
        }
        return null;
    }

    public ConsultaDTO adicionarConsultaListaAnimal(Long idAnimal, Consulta consulta) {
        Optional<Animal> animalOptional = animalRepository.findById(idAnimal);

        if (animalOptional.isPresent()) {
            Animal animal = animalOptional.get();
            
            animal.addConsulta(consulta);
            consultaRepository.save(consulta);
            animalRepository.save(animal);

            return consulta.converterConsultaDTO();
        }
        return null;
    }

    public List<AnimalDTO> exibirListaDeAnimaisDoTutor(Long idTutor) {
        Optional<Tutor> tutorOptional = tutorRepository.findById(idTutor);

        if (tutorOptional.isPresent()) {
            Tutor tutor = tutorOptional.get();
            return tutor.getAnimais().stream().map(animal -> new AnimalDTO(animal.getNome(), animal.getEspecie(), animal.getRaca(), animal.getGenero())).collect(Collectors.toList());
            
        } else {
            return Collections.emptyList();
        }
    }

    public Animal atualizarDadosDoAnimal(Long id, Animal animal) {
        Optional<Animal> animalOptional = animalRepository.findById(id);

        if (animalOptional.isPresent()) {
            Animal animalEncontrado = animalOptional.get();
    
                animalEncontrado.setNome(animal.getNome());
                animalEncontrado.setEspecie(animal.getEspecie());
                animalEncontrado.setRaca(animal.getRaca());
                animalEncontrado.setGenero(animal.getGenero());
                animalEncontrado.setDataDeNascimento(animal.getDataDeNascimento());
                animalEncontrado.setPeso(animal.getPeso());
                animalEncontrado.setCorDoPelo(animal.getCorDoPelo());

                return animalRepository.save(animalEncontrado);
        }
        return null;
    }

    @Transactional
    public String excluirAnimal(Long idTutor, Long idAnimal) {
        
        Tutor tutor = tutorRepository.findById(idTutor).orElse(null);
        Animal animal = animalRepository.findById(idAnimal).orElse(null);

        if (tutor == null) {
            return "Tutor não encontrado!";
        }

        if (animal == null) {
            return "Animal não encontrado!";
        }

        List<Animal> animais = tutor.getAnimais();
        if (animais.remove(animal)) {
            tutorRepository.save(tutor);
            animalRepository.deleteById(idAnimal);
            return "Animal removido com sucesso!";
        }
        return "Animal não encontrado na lista do tutor!";
    }

    @Autowired
    private AnimalRepository animalRepository;

    @Autowired
    private TutorRepository tutorRepository;

    @Autowired 
    private VacinaRepository vacinaRepository;

    @Autowired 
    private VermifugacaoRepository vermifugacaoRepository;

    @Autowired
    private CarrapaticidaRepository carrapaticidaRepository;

    @Autowired
    private ConsultaRepository consultaRepository;

    @Autowired
    private RacaoRepository racaoRepository;

    @Autowired
    private RacaoService racaoService;

    @Autowired 
    private VacinaService vacinaService;
    
    @Autowired
    private VermifugacaoService vermifugacaoService;

    @Autowired
    private CarrapaticidaService carrapaticidaService;
}
