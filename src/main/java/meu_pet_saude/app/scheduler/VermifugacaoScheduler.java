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
import meu_pet_saude.app.model.Vermifugacao;
import meu_pet_saude.app.repository.VermifugacaoRepository;

@Component
@EnableScheduling
public class VermifugacaoScheduler {
    
    @Scheduled(cron = CRON_EXPRESSION, zone = TIME_ZONE)
    public void enviarNotificacaoVermifugo() {

        LocalDate hoje = LocalDate.now();
        LocalDate mesesDepois = hoje.minusMonths(6);

        List<Vermifugacao> vermifugosParaReforco = vermifugacaoRepository.findByData(mesesDepois);

        for (Vermifugacao vermifugacao : vermifugosParaReforco) {
            enviarEmail(vermifugacao);
        }

    }

    public void enviarEmail(Vermifugacao vermifugacao) {
        Tutor tutor = vermifugacao.getAnimal().getTutor();

        SimpleMailMessage email = new SimpleMailMessage();
        email.setSubject("Lembrete de vermifugo!");
        email.setTo(tutor.getEmail());
        email.setFrom(EMAIL_SENDER);
        email.setText("Hoje é o dia de " + vermifugacao.getAnimal().getNome() + " tomar a dose de reforco do vermifugo!");

        javaMailSender.send(email);
    }

    private final String CRON_EXPRESSION = "0 0 0 * * *";

    private final String TIME_ZONE = "America/Sao_Paulo";

    @Value("${spring.mail.sender}")
    private String EMAIL_SENDER;

    @Autowired
    private VermifugacaoRepository vermifugacaoRepository;

    @Autowired
    private JavaMailSender javaMailSender;


}
