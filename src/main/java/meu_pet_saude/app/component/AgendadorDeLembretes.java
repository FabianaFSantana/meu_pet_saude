package meu_pet_saude.app.component;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import meu_pet_saude.app.service.EmailService;
import meu_pet_saude.app.service.VacinaService;

@Component
public class AgendadorDeLembretes {
    
    @Autowired
    private EmailService emailService;

    @Autowired
    private VacinaService vacinaService;
}
