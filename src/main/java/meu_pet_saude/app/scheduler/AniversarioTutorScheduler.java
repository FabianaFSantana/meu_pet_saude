package meu_pet_saude.app.scheduler;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import meu_pet_saude.app.model.Tutor;
import meu_pet_saude.app.repository.TutorRepository;

@Component
@EnableScheduling
public class AniversarioTutorScheduler {
    
    @Scheduled(cron = CRON_EXPRESSION, zone = TIME_ZONE)
    public void enviarNotificacao() {
    
        LocalDate hoje = LocalDate.now();

        tutorRepository.findByDiaMesNascimento(hoje.getDayOfMonth(), hoje.getMonthValue()).forEach(tutor -> enviarEmail(tutor));
    }

    public void enviarEmail(Tutor tutor) {
        SimpleMailMessage email = new SimpleMailMessage();
        email.setSubject("Uma mensagem para você!");
        email.setFrom(EMAIL_SENDER);
        email.setTo(tutor.getEmail());
        email.setText("Feliz aniversário, " + tutor.getNome() + "!");

        javaMailSender.send(email);
    }

    private final String CRON_EXPRESSION = "0 0 0 * * *"; //para determinar o horário

    private final String TIME_ZONE = "America/Sao_Paulo";//para determinar o fuso

    @Value("${spring.mail.sender}")
    private String EMAIL_SENDER;

    @Autowired 
    private TutorRepository tutorRepository;

    @Autowired
    private JavaMailSender javaMailSender;

}