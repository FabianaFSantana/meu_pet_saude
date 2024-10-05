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

import meu_pet_saude.app.model.Racao;
import meu_pet_saude.app.model.Tutor;
import meu_pet_saude.app.repository.RacaoRepository;

@Component
@EnableScheduling
public class RacaoScheduller {
    
    @Scheduled(cron = CRON_EXPRESSION, zone = TIME_ZONE)
    public void enviarLembreteDeCompraDeRacao() {
        LocalDate hoje = LocalDate.now();

        List<Racao> racoesParaLembreteDeCompra = racaoRepository.findByDataProximaCompra(hoje);

        for (Racao racao : racoesParaLembreteDeCompra) {
            enviarEmail(racao);
        }
    }


    public void enviarEmail(Racao racao) {
        Tutor tutor = racao.getAnimal().getTutor();

        SimpleMailMessage email = new SimpleMailMessage();
        email.setSubject("Lembrete de compra de Ração");
        email.setFrom(EMAIL_SENDER);
        email.setTo(tutor.getEmail());
        email.setText("Hoje é dia de repor a ração de " + racao.getAnimal().getNome() + "! No dia " + racao.getDataUltimaCompra() + " foi a última vez que você comprou a ração " + racao.getNomeRacao() + "de peso " + racao.getPeso() + " kg, no valor de R$ " + racao.getPreco() + " na loja " + racao.getLoja());

        javaMailSender.send(email);
    }

    private final String CRON_EXPRESSION = "0 23 4 * * *";

    private final String TIME_ZONE = "America/Sao_Paulo";

    @Value("$(spring.mail.sender)")
    private String EMAIL_SENDER;

    @Autowired
    private RacaoRepository racaoRepository;

    @Autowired
    private JavaMailSender javaMailSender;
}
