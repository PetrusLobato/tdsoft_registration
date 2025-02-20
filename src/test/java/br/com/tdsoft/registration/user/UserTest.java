package br.com.tdsoft.registration.user;

import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.MediaType;
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import com.fasterxml.jackson.databind.ObjectMapper;
import at.favre.lib.crypto.bcrypt.BCrypt;




@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc()
public class UserTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private WebApplicationContext context;

    @Before
    public void setup(){
        mvc = MockMvcBuilders
        .webAppContextSetup(context)
        .apply(SecurityMockMvcConfigurers.springSecurity())
        .build();
    }

    @Autowired
    private IUserRepository userRepository;


    @Test
    public void create_new_user_test() throws Exception{

        var createUserTest = UserEntity.builder()
        .name("NAME_TESTE")
        .password("SENHA_TESTE")
        .email("test@gmail.com")
        .build();


        mvc.perform(MockMvcRequestBuilders.post("/user/create")
       .contentType(MediaType.APPLICATION_JSON)
       .content(new ObjectMapper().writeValueAsString(createUserTest))
       .header("basic", createUserTest))
       .andExpect(MockMvcResultMatchers.status().isCreated());
       
    }


    @Test
    public void login_test() throws Exception{

        // Cria um usuario, para garantir que o usuario exista no bancode dados, para o teste funcionar.
        userRepository.save(UserEntity.builder()
        .name("Teste")
        .email("test10@gmail.com")
        .password(BCrypt.withDefaults().hashToString(12, "SENHA_TESTE".toCharArray()))
        .build());

        var loginusertest = UserEntity.builder()
        .password(BCrypt.withDefaults().hashToString(12, "SENHA_TESTE".toCharArray()))
        .email("teste10@gmail.com")
        .build();

       mvc.perform(MockMvcRequestBuilders.post("/user/login")
       .contentType(MediaType.APPLICATION_JSON)
       .content(new ObjectMapper().writeValueAsString(loginusertest)))
       .andExpect(MockMvcResultMatchers.content().string(org.hamcrest.Matchers.notNullValue()));

    }


    @Test
    public void update_user_test() throws Exception{

        // Cria um usuario, para garantir que o usuario exista no bancode dados, para o teste funcionar.
        var result = userRepository.save(UserEntity.builder()
        .name("Teste")
        .email("test120@gmail.com")
        .password(BCrypt.withDefaults().hashToString(12, "SENHA_TESTE".toCharArray()))
        .build());

        // Para garantir a permissão o email deve ser o mesmo.
        var updateUserTest = UserEntity.builder()
        .name("NAME_TESTE_NOVO")
        .password(BCrypt.withDefaults().hashToString(12, "NOVA_SENHA_TESTE".toCharArray()))
        .email("test120@gmail.com")
        .id(result.getId())
        .build();

       mvc.perform(MockMvcRequestBuilders.put("/user/" + result.getId())
       .contentType(MediaType.APPLICATION_JSON)
       .content(new ObjectMapper().writeValueAsString(updateUserTest)))
       .andExpect(MockMvcResultMatchers.status().isOk());
       

    }


    
    
}
