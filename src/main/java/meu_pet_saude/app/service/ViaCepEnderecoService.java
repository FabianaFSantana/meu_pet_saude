package meu_pet_saude.app.service;


import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import meu_pet_saude.app.dto.EnderecoDTO;

@Service
public class ViaCepEnderecoService {

    public EnderecoDTO buscarEnderecoPeloCep(String cep) {
        RestTemplate restTemplate = new RestTemplate();

        String url = String.format("https://viacep.com.br/ws/%s/json/", cep);
        ResponseEntity<EnderecoDTO> response = restTemplate.getForEntity(url, EnderecoDTO.class);

        return response.getBody();
    }
        
}
