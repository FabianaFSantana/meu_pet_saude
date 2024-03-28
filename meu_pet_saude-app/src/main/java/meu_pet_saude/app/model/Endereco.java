package meu_pet_saude.app.model;

import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Embeddable;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor@AllArgsConstructor
@Embeddable

@JsonIgnoreProperties(ignoreUnknown = true) 
public class Endereco {
    
    private String cep;
    private String endereco;
    private String numero;
    private String complemento;
    private String bairro;
    private String cidade;
    private String estado;
    private String pais;

    public void buscarEnderecoPorCep(String cep) {
        try {
            String url = "https://viacep.com.br/ws/" + cep + "/json/";
        RestTemplate restTemplate =new RestTemplate();
        ViaCepEndereco viaCepEndereco = restTemplate.getForObject(url, ViaCepEndereco.class);

        if (viaCepEndereco != null) {

            this.endereco = viaCepEndereco.getLogradouro();
            this.bairro = viaCepEndereco.getBairro();
            this.cidade = viaCepEndereco.getLocalidade();
            this.estado = viaCepEndereco.getUf();
        }
            
        } catch (Exception e) {
            throw new EntityNotFoundException("Cep não encontrado." + e.getMessage());
        }
        
    }

}
