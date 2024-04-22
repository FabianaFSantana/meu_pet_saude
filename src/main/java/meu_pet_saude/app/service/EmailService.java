package meu_pet_saude.app.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import meu_pet_saude.app.model.Animal;
import meu_pet_saude.app.model.CarrapatoPulga;
import meu_pet_saude.app.model.Tutor;
import meu_pet_saude.app.model.Vacina;
import meu_pet_saude.app.model.Vermifugacao;


@Service
public class EmailService {

    @Autowired
    private JavaMailSender javaMailSender;


    @Value("${spring.mail.host}")
    private String host;

    @Value("${spring.mail.port}")
    private int porta;

    @Value("${spring.mail.username}")
    private String destinatario;

    @Value("${spring.mail.password}")
    private String senha;

    public void enviarEmail(String destinatario, String assunto, String mensagem) {
        
        SimpleMailMessage mailMessage = new SimpleMailMessage();

        mailMessage.setTo(destinatario);
        mailMessage.setSubject(assunto);
        mailMessage.setText(mensagem);

        javaMailSender.send(mailMessage);

    }

    public void enviarEmailDeRefocoDeVacina(Tutor tutor, Vacina vacina, Animal animal) {

        try {
            String destinatario = tutor.getEmail();
            String assunto = "Nova Notificação: " + vacina.getNomeVacina();
            String mensagem = "Olá, " + tutor.getNome() + "!\n\n" +
                              "Hoje é o dia da vacina de " + animal.getNome() +"!\n" +
                              "Nome da vacina: " + vacina.getNomeVacina() + "\n" +
                              "Data da última dose: " + vacina.getDataDaUltimaDose() + "\n" +
                              "Data da próxima dose: " + vacina.getDataDaProximaDose() + "\n";
            
            try {

                enviarEmail(destinatario, assunto, mensagem);
                                
            } catch (Exception e) {
                e.printStackTrace();
            }

        } catch (Exception e) {
            throw new RuntimeException("Falha ao enviar o email.");
        }

    }


    public void enviarEmailDeRefocoDeVermifugacao(Tutor tutor, Vermifugacao vermifugo, Animal animal) {

        try {
            String destinatario = tutor.getEmail();
            String assunto = "Nova notificação: " + vermifugo.getNomeMedic();
            String mensagem = "Olá, " + tutor.getNome() + "!\n\n" +
                              "Hoje é o dia da dose de reforço do vermífugo de " + animal.getNome() + "!\n" +
                              "Data da última dose: " + vermifugo.getData() + "\n" +
                              "Medicamento: " + vermifugo.getNomeMedic() + "\n" +
                              "Data da próxima dose: " + vermifugo.getProximaDose() + "\n";
            
            try {
                enviarEmail(destinatario, assunto, mensagem);

            } catch (Exception e) {
               e.printStackTrace();
            }

        } catch (Exception e) {
            throw new RuntimeException("Erro ao enviar email.");
        }
    }

    public void enviarEmailDeRefocoDeCarrapaticida(Tutor tutor, CarrapatoPulga carrapaticida, Animal animal) {
        
        try {
            String destinatario = tutor.getEmail();
            String assunto = carrapaticida.getNomeMedic();
            String mensagem = "Olá " + tutor.getNome() + "!\n\n" +
                           "Hoje é o dia da dose de reforço do carrapaticida de " + animal.getNome() + "!\n" +
                           "Medicamento: " + carrapaticida.getNomeMedic() +"\n" +
                           "Data da última dose: " + carrapaticida.getData() + "\n" +
                           "Data da prósima dose: " + carrapaticida.getProximaDose() + "\n";
            
            try {
                enviarEmail(destinatario, assunto, mensagem);
            } catch (Exception e) {
               e.printStackTrace();
            }
        } catch (Exception e) {
            throw new RuntimeException("Email não pôde ser enviado.");
        }
    }

 
    
}
