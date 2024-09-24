package meu_pet_saude.app.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import meu_pet_saude.app.repository.RacaoRepository;

@Service
public class RacaoService {

    @Autowired
    private RacaoRepository racaoRepository;
    
}
