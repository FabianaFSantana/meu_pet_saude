package meu_pet_saude.app.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import meu_pet_saude.app.dto.EnderecoDTO;
import meu_pet_saude.app.dto.TutorDTO;
import meu_pet_saude.app.model.Animal;
import meu_pet_saude.app.model.Endereco;
import meu_pet_saude.app.model.Tutor;
import meu_pet_saude.app.repository.AnimalRepository;
import meu_pet_saude.app.repository.TutorRepository;

@Service
public class TutorService {
    
    public Tutor salvarTutor(Tutor tutor) {
        String senha = tutor.getSenha();

        BCryptPasswordEncoder encoder = autentificacaoService.getPasswordEncoder();

        String senhaCriptografada = encoder.encode(senha);

        tutor.setSenha(senhaCriptografada);

        Endereco endereco = tutor.getEndereco();
        EnderecoDTO enderecoDTO = viaCepEnderecoService.buscarEnderecoPeloCep(tutor.getEndereco().getCep());

        endereco.setBairro(enderecoDTO.getBairro());
        endereco.setLogradouro(enderecoDTO.getLogradouro());
        endereco.setCidade(enderecoDTO.getLocalidade());
        endereco.setUf(enderecoDTO.getUf());

        return tutorRepository.save(tutor);
    }

    public Tutor buscaTutorPeloEmail(String email) {
        Optional<Tutor> tutorOptional = tutorRepository.findByEmail(email);

        if (tutorOptional.isPresent()) {
            Tutor tutor = tutorOptional.get();
            return tutor;
        }
        return null;
    }

    public Animal adicionarAnimalNaListaDeTutor(Long id, Animal animal) {
        Optional<Tutor> tutorOptional = tutorRepository.findById(id);

        if (tutorOptional.isPresent()) {
            Tutor tutor = tutorOptional.get();

            tutor.addAnimal(animal);
            animalRepository.save(animal);
            tutorRepository.save(tutor);

            return animal;   
        }
        return null;
    }

    public List<Tutor> buscarTutores() {
        return tutorRepository.findAll();
    }

    public TutorDTO buscarTutorPeloId(Long id) {
        Optional<Tutor> tutorOptional = tutorRepository.findById(id);

        if (tutorOptional.isPresent()) {
            Tutor tutor = tutorOptional.get();
            return tutor.converterTutorDTO();
        }
        return null;
    }

    public Tutor atualizarDadosTutor(Long id, Tutor tutor) {
        Optional<Tutor> tutorOptional = tutorRepository.findById(id);

        if (tutorOptional.isPresent()) {
            Tutor tutorEncontrado = tutorOptional.get();

            tutorEncontrado.setNome(tutor.getNome());
            tutorEncontrado.setDataDeNascimento(tutor.getDataDeNascimento());
            tutorEncontrado.setTelefone(tutor.getTelefone());
            tutorEncontrado.setEmail(tutor.getEmail());
            
            Endereco endereco = tutorEncontrado.getEndereco();
            EnderecoDTO enderecoDTO = viaCepEnderecoService.buscarEnderecoPeloCep(tutor.getEndereco().getCep());

            endereco.setCep(enderecoDTO.getCep());
            endereco.setBairro(enderecoDTO.getBairro());
            endereco.setCidade(enderecoDTO.getLocalidade());
            endereco.setLogradouro(enderecoDTO.getLogradouro());
            endereco.setUf(enderecoDTO.getUf());
            endereco.setNumero(tutor.getEndereco().getNumero());
            endereco.setComplemento(tutor.getEndereco().getComplemento());
           
            return tutorRepository.save(tutorEncontrado);
        }
        return null;
    }

    public String excluirTutor(Long id) {
        tutorRepository.deleteById(id);
        return "Tutor excluído com sucesso!";
    }
    
    @Autowired
    private TutorRepository tutorRepository;

    @Autowired
    private ViaCepEnderecoService viaCepEnderecoService;

    @Autowired
    private AnimalRepository animalRepository;

    @Autowired
    private AutentificacaoService autentificacaoService;
}
