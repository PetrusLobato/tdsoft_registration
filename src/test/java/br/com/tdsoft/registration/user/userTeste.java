package br.com.tdsoft.registration.user;

import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import br.com.tdsoft.registration.utils.UtilsTest;





@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class userTeste {

    private MockMvc mvc;

    @Autowired
    private WebApplicationContext context;

    @Before
    public void setup(){
        mvc = MockMvcBuilders.webAppContextSetup(context).build();
    }

    @InjectMocks
    private UserController userController;

    @Mock
    private IUserRepository userRepository;



    @Test
    public void create_new_user() throws Exception{

        var createUserTest = UserEntity.builder()
        .name("NAME_TESTE")
        .password("SENHA_TESTE")
        .email("teste@gmail.com")
        .build();

       mvc.perform(MockMvcRequestBuilders.post("/user/create")
       .contentType(MediaType.APPLICATION_JSON)
       .content(UtilsTest.objectToJson(createUserTest)))
       .andExpect(MockMvcResultMatchers.status().isOk());
       

    }

    
    
}
