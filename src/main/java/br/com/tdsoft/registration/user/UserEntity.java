package br.com.tdsoft.registration.user;

import java.time.LocalDateTime;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
@Entity(name = "table_users" )
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    private String name;
    private String password;

    @Column(unique = true, nullable = false)
    @NotEmpty(message = "O campo de e-mail não pode ser nulo")
    @Email(message = "Deve colocar um email valido, ex: exemplo@gmail.com")
    private String email;
    
    @CreationTimestamp
    private LocalDateTime createdAt;
    
}

