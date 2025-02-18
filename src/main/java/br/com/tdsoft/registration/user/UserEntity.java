package br.com.tdsoft.registration.user;

import java.time.LocalDateTime;
import java.util.UUID;
import org.hibernate.annotations.CreationTimestamp;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Schema.RequiredMode;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Email;
import lombok.Data;

@Data
@Entity(name = "table_users" )
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Schema(example = "meuNome")
    private String name;

    @Schema(example = "minhasenha123", requiredMode = RequiredMode.REQUIRED)
    private String password;

    @Column(unique = true, nullable = false)
    @Email(message = "Deve colocar um email valido, ex: exemplo@gmail.com")
    @Schema(example = "exemplo@gmail.com", requiredMode = RequiredMode.REQUIRED)
    private String email;
    
    @CreationTimestamp
    private LocalDateTime createdAt;
    
}

