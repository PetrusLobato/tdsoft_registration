package br.com.tdsoft.registration.cliente;

import java.util.Base64;

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
import br.com.tdsoft.registration.user.IUserRepository;
import br.com.tdsoft.registration.user.UserEntity;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc()
public class ClienteTest {
    

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
    private IClienteRepository clienteRepository;

    @Autowired
    private IUserRepository userRepository;


    @Test
    public void create_new_cliente_test() throws Exception{

        // Precisa existir um usuario para poder cadastrar um cliente, porque é um relacionamento 1:N. 
        var result = userRepository.save(UserEntity.builder()
        .name("USER_CLIENTE")
        .email("user_cliente@gmail.com")
        .password(BCrypt.withDefaults().hashToString(12, "SENHA_CLIENTE".toCharArray()))
        .build());

        var createClienteTest = ClienteEntity.builder()
        .name("NAME_CLIENTE_TESTE")
        .description("DESCRIÇÃO_TESTE")
        .build();

        String auth = "Basic " + Base64.getEncoder().encodeToString((result.getEmail() + ":" + result.getPassword()).getBytes());


        mvc.perform(MockMvcRequestBuilders.post("/cliente/create")
       .contentType(MediaType.APPLICATION_JSON)
       .content(new ObjectMapper().writeValueAsString(createClienteTest))
       .header("Authorization", auth))
       .andExpect(MockMvcResultMatchers.status().isCreated());
       
    }


    @Test
    public void list_client_per_user_test() throws Exception{

           // Precisa existir um usuario para poder fazer a busca dos seus cliente. 
           var result = userRepository.save(UserEntity.builder()
           .name("USER_CLIENTE")
           .email("user_cliente@gmail.com")
           .password(BCrypt.withDefaults().hashToString(12, "SENHA_CLIENTE".toCharArray()))
           .build());
   
   
           String auth = "Basic " + Base64.getEncoder().encodeToString((result.getEmail() + ":" + result.getPassword()).getBytes());
   
   
           mvc.perform(MockMvcRequestBuilders.get("/cliente/userlist")
          .contentType(MediaType.APPLICATION_JSON)
          .header("Authorization", auth))
          .andExpect(MockMvcResultMatchers.status().isOk());

    }


    @Test
    public void delete_client_test_not_authorization() throws Exception{

         
        // Precisa existir um usuario para poder  deletar o cliente. 
        var result = userRepository.save(UserEntity.builder()
        .name("USER_CLIENTE")
        .email("user_cliente@gmail.com")
        .password(BCrypt.withDefaults().hashToString(12, "SENHA_CLIENTE".toCharArray()))
        .build());

        String auth = "Basic " + Base64.getEncoder().encodeToString((result.getEmail() + ":" + result.getPassword()).getBytes());
      
        // Precisa criar um cliente para poder deletar o mesmo, porem com id aleatorio.
        var createClienteTest = clienteRepository.save(ClienteEntity.builder()
        .name("NAME_CLIENTE_TESTE")
        .description("DESCRIÇÃO_TESTE")
        .idUser(result.getId())
        .build());



        mvc.perform(MockMvcRequestBuilders.delete("/cliente/" + createClienteTest.getId())
       .contentType(MediaType.APPLICATION_JSON)
       .header("Authorization", auth))
       .andExpect(MockMvcResultMatchers.status().isForbidden());

    }


}
