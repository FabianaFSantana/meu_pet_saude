package meu_pet_saude.app.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import meu_pet_saude.app.dto.AutenticacaoDTO;
import meu_pet_saude.app.dto.TutorDTO;
import meu_pet_saude.app.model.Tutor;
import meu_pet_saude.app.service.TokenService;

@RestController
@RequestMapping("/auth")
public class AutenticacaoController {
    
    @PostMapping("/login")
    public ResponseEntity<TutorDTO> login(@Valid @RequestBody AutenticacaoDTO dto) {

        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(dto.getUsername(), dto.getPassword()));

        Tutor tutor = (Tutor) authentication.getPrincipal();
        String token = tokenService.gerarToken(tutor);

        TutorDTO tutorDTO = tutor.converterTutorDTO();
        tutorDTO.setToken(token);

        return ResponseEntity.status(HttpStatus.OK).body(tutorDTO);
    }


    @Autowired
    private TokenService tokenService;

    @Autowired
    private AuthenticationManager authenticationManager;
}
