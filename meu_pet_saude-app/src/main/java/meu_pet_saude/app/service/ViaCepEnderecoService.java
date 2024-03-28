package meu_pet_saude.app.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import jakarta.persistence.EntityNotFoundException;
import meu_pet_saude.app.model.Endereco;
import meu_pet_saude.app.model.Tutor;
import meu_pet_saude.app.model.ViaCepEndereco;
import meu_pet_saude.app.repository.TutorRepository;

@Service
public class ViaCepEnderecoService {

    @Autowired
    private TutorRepository tutorRepository;

    public void buscarEnderecoPorCep(Long id, String cep) {

        Optional<Tutor> tutorOptional = tutorRepository.findById(id);

        if (tutorOptional.isPresent()) {
            Tutor tutorEncont = tutorOptional.get();

            try {
                String url = "https://viacep.com.br/ws/" + cep + "/json/";
                RestTemplate restTemplate = new RestTemplate();
                ViaCepEndereco viaCepEndereco = restTemplate.getForObject(url, ViaCepEndereco.class);
    
                if (viaCepEndereco != null) {
                    Endereco endereco = tutorEncont.getEndereco();
    
                    endereco.setCep(viaCepEndereco.getCep());
                    endereco.setEndereco(viaCepEndereco.getLogradouro());
                    endereco.setBairro(viaCepEndereco.getBairro());
                    endereco.setCidade(viaCepEndereco.getLocalidade());
                    endereco.setEstado(viaCepEndereco.getUf());

                    tutorRepository.save(tutorEncont);
                    
                }
    
            } catch (Exception e) {
                throw new RuntimeException("Erro ao procurar o cep" + e.getMessage());
            }
            
           
    
        } else {
            throw new EntityNotFoundException("Tutor não encontrdo com este id:" + id);
        }
    
            
        }
        

}
