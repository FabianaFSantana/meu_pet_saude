package meu_pet_saude.app.service;

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
    
    @Autowired
    private TutorRepository tutorRepository;

    @Autowired
    private ViaCepEnderecoService viaCepEnderecoService;
}
