package meu_pet_saude.app.scheduler;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import meu_pet_saude.app.model.Tutor;
import meu_pet_saude.app.model.Vacina;
import meu_pet_saude.app.repository.VacinaRepository;

@Component
@EnableScheduling
public class VacinaScheduler {

    @Scheduled(cron = CRON_EXPRESSION, zone = TIME_ZONE)
    public void enviarNotificacaoVacina() {

        LocalDate hoje = LocalDate.now();

        List<Vacina> vacinasParaReforco = vacinaRepository.findByProximaDose(hoje);

        for (Vacina vacina : vacinasParaReforco) {
            enviarEmailVacina(vacina);
        }
        
    }

    public void enviarEmailVacina(Vacina vacina) {

        Tutor tutor = vacina.getAnimal().getTutor();
        
        SimpleMailMessage email = new SimpleMailMessage();
        email.setSubject("Lembrete de Vacina!");
        email.setFrom(EMAIL_SENDER);
        email.setTo(tutor.getEmail());
        email.setText("Hoje é dia de " + vacina.getAnimal().getNome() + " tomar a dose de reforço da " + vacina.getNomeVacina());

        javaMailSender.send(email);
    }

    private final String CRON_EXPRESSION = "0 05 06 * * *";

    private final String TIME_ZONE = "America/Sao_Paulo";

    @Value("${spring.mail.sender}")
    private String EMAIL_SENDER;

    @Autowired
    private VacinaRepository vacinaRepository;

    @Autowired
    private JavaMailSender javaMailSender;
    
}
