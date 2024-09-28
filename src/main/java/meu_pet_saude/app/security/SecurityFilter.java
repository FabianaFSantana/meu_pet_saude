package meu_pet_saude.app.security;

import java.io.IOException;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import meu_pet_saude.app.service.AutentificacaoService;
import meu_pet_saude.app.service.TokenService;

@Component
public class SecurityFilter extends OncePerRequestFilter{
    
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        
        try {
            String token = _obterTokenDaRequisicao(request);

            if (Objects.nonNull(token)) {
                String email = tokenService.obterEmailTutor(token);
                UserDetails tutor = autentificacaoService.loadUserByUsername(email);
    
                Authentication authentication = new UsernamePasswordAuthenticationToken(tutor, null, tutor.getAuthorities());
    
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Erro de autenticação: " + e.getMessage());
            return;
        }
       
        filterChain.doFilter(request, response);
    }

    private String _obterTokenDaRequisicao(HttpServletRequest requisicao) {
        String authorization = requisicao.getHeader("Authorization");

        if (Objects.nonNull(authorization)) {
            return authorization.replace("Bearer ", "");
        }
        return null;
    }

    @Autowired
    private TokenService tokenService;

    @Autowired
    private AutentificacaoService autentificacaoService;

   
}
