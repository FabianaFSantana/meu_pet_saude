package meu_pet_saude.app.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import meu_pet_saude.app.dto.EnderecoDTO;
import meu_pet_saude.app.model.Endereco;
import meu_pet_saude.app.model.Tutor;
import meu_pet_saude.app.repository.TutorRepository;

@Service
public class TutorService {
    
    public Tutor salvarTutor(Tutor tutor) {

        Endereco endereco = tutor.getEndereco();
        EnderecoDTO enderecoDTO = viaCepEnderecoService.buscarEnderecoPeloCep(tutor.getEndereco().getCep());

        endereco.setBairro(enderecoDTO.getBairro());
        endereco.setLogradouro(enderecoDTO.getLogradouro());
        endereco.setCidade(enderecoDTO.getLocalidade());
        endereco.setUf(enderecoDTO.getUf());

        return tutorRepository.save(tutor);
    }

    public List<Tutor> buscarTutores() {
        return tutorRepository.findAll();
    }

    public Tutor buscarTutorPeloId(Long id) {
        Optional<Tutor> tutorOptional = tutorRepository.findById(id);

        if (tutorOptional.isPresent()) {
            Tutor tutor = tutorOptional.get();
            return tutor;
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
            tutorEncontrado.getEndereco().setCep(tutor.getEndereco().getCep());
            tutorEncontrado.getEndereco().setLogradouro(tutor.getEndereco().getLogradouro());
            tutorEncontrado.getEndereco().setNumero(tutor.getEndereco().getNumero());
            tutorEncontrado.getEndereco().setComplemento(tutor.getEndereco().getComplemento());
            tutorEncontrado.getEndereco().setBairro(tutor.getEndereco().getBairro());
            tutorEncontrado.getEndereco().setCidade(tutor.getEndereco().getCidade());
            tutorEncontrado.getEndereco().setUf(tutor.getEndereco().getUf());
           
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
}
