package meu_pet_saude.app.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import meu_pet_saude.app.model.Tutor;
import meu_pet_saude.app.repository.TutorRepository;

@Service
public class AutentificacaoService implements UserDetailsService{

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Tutor> tutorOptional = tutorRepository.findByEmail(username);

        if (tutorOptional.isPresent()) {
            return tutorOptional.get();
        }
        return null;
    }

    public BCryptPasswordEncoder getPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }


    @Autowired
    private TutorRepository tutorRepository;
    
}
