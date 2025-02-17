package br.com.tdsoft.registration.user;

import java.nio.charset.StandardCharsets;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import at.favre.lib.crypto.bcrypt.BCrypt;
import br.com.tdsoft.registration.utils.Utils;



@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private IUserRepository userRepository;
    
    @PostMapping("/create")
    public ResponseEntity<?> createUser(@RequestBody UserEntity userEntity) {

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
    public ResponseEntity<?>loginUser(@RequestBody UserEntity userEntity) {

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
       
        
        return ResponseEntity.status(HttpStatus.OK).body("TOken: 9012912612789361287936128907");
        

        
    }


    @PutMapping("/{id}")
    public ResponseEntity<?> updateUser (@RequestBody UserEntity userEntity, @PathVariable UUID id){

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
