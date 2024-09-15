package meu_pet_saude.app.model;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@Entity(name = "tutor")
public class Tutor implements UserDetails{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    private String nome;

    @Column(nullable = false)
    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate dataDeNascimento;

    @Column(nullable = false)
    private String telefone;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String senha;

    @Embedded
    private Endereco endereco;

    @Column(nullable =  false)
    private Boolean administrador;

    @Column(nullable =  false)
    private Boolean usuarioExterno;

    @OneToMany(cascade = {CascadeType.ALL, CascadeType.REMOVE}, orphanRemoval = true)
    @JoinColumn(name = "tutor_id")
    private List<Animal> animais;

    public Tutor() {
        this.administrador = Boolean.FALSE;
        this.usuarioExterno = Boolean.TRUE;
    }

    public void addAnimal(Animal animal) {
        this.animais.add(animal);
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if (administrador) {
            return List.of(new SimpleGrantedAuthority("ROLE_ADMIN"));
        }

        if (usuarioExterno) {
            return List.of(new SimpleGrantedAuthority("ROLE_EXT_USER"));
        }
        return null;
    }

    @Override
    public String getPassword() {
      return senha;
    }

    @Override
    public String getUsername() {
       return email;
    }

    @Override
    public boolean isAccountNonExpired() {
       return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
