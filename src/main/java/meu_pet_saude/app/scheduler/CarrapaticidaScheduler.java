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

import meu_pet_saude.app.model.Carrapaticida;
import meu_pet_saude.app.model.Tutor;
import meu_pet_saude.app.repository.CarrapaticidaRepository;

@Component
@EnableScheduling
public class CarrapaticidaScheduler {
    
    @Scheduled(cron = CRON_EXPRESSION, zone = TIME_ZONE)
    public void enviarNotificacaoDeCarrapaticida() {
        LocalDate hoje = LocalDate.now();
        LocalDate umMesAtras = hoje.minusMonths(1);

        List<Carrapaticida> carrapaticidasReforco = carrapaticidaRepository.findByData(umMesAtras);

        for (Carrapaticida carrapaticida : carrapaticidasReforco) {
            enviarEmail(carrapaticida);
        }

    }

    public void enviarEmail(Carrapaticida carrapaticida) {
        Tutor tutor = carrapaticida.getAnimal().getTutor();

        SimpleMailMessage email = new SimpleMailMessage();
        email.setSubject("Lembrete de reforço de Carrapaticida!");
        email.setFrom(EMAIL_SENDER);
        email.setTo(tutor.getEmail());
        email.setText("Hoje é o dia da dose de reforço do carrapaticida de " + carrapaticida.getAnimal().getNome());

        javaMailSender.send(email);
    }

    private final String CRON_EXPRESSION = "0 05 06 * * *";

    private final String TIME_ZONE = "America/Sao_Paulo";

    @Value("${spring.mail.sender}")
    private String EMAIL_SENDER;

    @Autowired
    private CarrapaticidaRepository carrapaticidaRepository;

    @Autowired
    private JavaMailSender javaMailSender;
}
