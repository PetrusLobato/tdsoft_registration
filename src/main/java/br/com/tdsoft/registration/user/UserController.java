package br.com.tdsoft.registration.user;

import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import at.favre.lib.crypto.bcrypt.BCrypt;
import br.com.tdsoft.registration.utils.Utils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;



@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private IUserRepository userRepository;

    @Value("${security.token.secret}")
    private String secret;

    
    @PostMapping("/create")
    @Tag(name = "User", description = "Create, update and login")
    @Operation(summary = "Criação de usuario", description = "Cria apenas um usuario por e-mail")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Usuário criado com sucesso", content = @Content(schema = @Schema(implementation = UserEntity.class))),
        @ApiResponse(responseCode = "400", description = "Email já cadastrado", content = @Content)
    })
    public ResponseEntity<?> createUser(@Valid @RequestBody UserEntity userEntity) {

        var validadedEmail = this.userRepository.findByEmail(userEntity.getEmail());

        if (validadedEmail != null) {
            
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Email já cadastrado");

        }

        var passwordHashred =  BCrypt.withDefaults().hashToString(12, userEntity.getPassword().toCharArray());


        userEntity.setPassword(passwordHashred);


        var resultCreateUser = this.userRepository.save(userEntity);
        
        return ResponseEntity.status(HttpStatus.CREATED).body(resultCreateUser);
        

        
    }


    @PostMapping("/login")
    @Tag(name = "User", description = "Create, update and login")
    @Operation(summary = "Login de acesso", description = "É gerado um token quando é logado ")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Usuário logado com sucesso", content = @Content(schema = @Schema(implementation = String.class))),
        @ApiResponse(responseCode = "400", description = "Email ou senha invalido", content = @Content)
    })
    public ResponseEntity<?> loginUser(@Valid @RequestBody UserEntity userEntity) {

        var validadedEmail = this.userRepository.findByEmail(userEntity.getEmail());

        if (validadedEmail == null) {
            
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Email ou senha incorreta");

        }

        byte[] passWordEntity = userEntity.getPassword().getBytes(StandardCharsets.UTF_8);
        byte[] passWordUser = validadedEmail.getPassword().getBytes(StandardCharsets.UTF_8);

        var passwordVerify = BCrypt.verifyer().verify(passWordEntity, passWordUser);

        if(!passwordVerify.verified){

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Email ou senha incorreta");
        }

        try {
                Algorithm algorithm = Algorithm.HMAC256(secret);
                String token = JWT.create()
                    .withIssuer("auth-registration")
                    .withSubject(validadedEmail.getId().toString())
                    .withExpiresAt(new Date(System.currentTimeMillis() + 86400000))
                    .sign(algorithm);

                return ResponseEntity.ok(token);

        } catch (JWTCreationException exception){
            throw new RuntimeException("Erro ao gerar o token", exception);
        }


        
    }


    @PutMapping("/{id}")
    @Tag(name = "User", description = "Create, update and login")
    @Operation(summary = "Atualização de dados", description = "É atualizado de acordo com o Id do usuario")
    public ResponseEntity<?> updateUser (@Valid @RequestBody UserEntity userEntity, @PathVariable UUID id){

        var userUpdate = this.userRepository.findById((id)).orElse(null);

        
        if(userUpdate == null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Usuario não encontrado");
        }

        if(!userUpdate.getEmail().equals(userEntity.getEmail())){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Você não tem permissão para essa tarefa o email deve ser compativel.");
        }


        if(userEntity.getPassword() != null){

            var passwordHashred =  BCrypt.withDefaults().hashToString(12, userEntity.getPassword().toCharArray());

            userEntity.setPassword(passwordHashred); 
            
        }

        Utils.copyNonNullProperties(userEntity, userUpdate);
        
        var result = this.userRepository.save(userUpdate);  


        return ResponseEntity.status(200).body(result);

        

        

        


    }


}
