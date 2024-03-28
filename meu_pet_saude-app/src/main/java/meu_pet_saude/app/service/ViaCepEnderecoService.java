package meu_pet_saude.app.service;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import jakarta.persistence.EntityNotFoundException;
import meu_pet_saude.app.model.Endereco;
import meu_pet_saude.app.model.ViaCepEndereco;

@Service
public class ViaCepEnderecoService {

    public Endereco buscarEnderecoPorCep(String cep) {
        try {
            String url = "https://viacep.com.br/ws/" + cep + "/json/";
            RestTemplate restTemplate = new RestTemplate();
            ViaCepEndereco viaCepEndereco = restTemplate.getForObject(url, ViaCepEndereco.class);

            if (viaCepEndereco != null) {
                Endereco endereco = new Endereco();

                endereco.setEndereco(viaCepEndereco.getLogradouro());
                endereco.setBairro(viaCepEndereco.getBairro());
                endereco.setCidade(viaCepEndereco.getLocalidade());
                endereco.setEstado(viaCepEndereco.getUf());
                return endereco;
            }

        } catch (Exception e) {
            throw new EntityNotFoundException("Cep não encontrado." + e.getMessage());
        }
        return null;

    }

}
