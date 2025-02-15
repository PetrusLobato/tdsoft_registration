package br.com.tdsoft.registration.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import at.favre.lib.crypto.bcrypt.BCrypt;



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


    


}
