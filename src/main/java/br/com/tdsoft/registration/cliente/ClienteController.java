package br.com.tdsoft.registration.cliente;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.tdsoft.registration.utils.Utils;
import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/cliente")
public class ClienteController {
    

    @Autowired
    private IClienteRepository clienteRepository;


    @PostMapping("/create")
    public ResponseEntity<?> createCliente (@RequestBody ClienteEntity clienteEntity, HttpServletRequest request) {

        var idUser = request.getAttribute("idUser");

        clienteEntity.setIdUser((UUID) idUser);

        var resultCliente = this.clienteRepository.save(clienteEntity);

        return ResponseEntity.status(HttpStatus.CREATED).body(resultCliente);

    }
    

    @GetMapping("/all")
    public ResponseEntity<?> listCliente(){

        var listClientes = this.clienteRepository.findAll();

        return ResponseEntity.status(HttpStatus.OK).body(listClientes);
    }


    @GetMapping("/userlist")
    public List<ClienteEntity> allUserList(HttpServletRequest request){
       

        var idUser = request.getAttribute("idUser");

        var clienteList = this.clienteRepository.findByIdUser((UUID) idUser);

        

        return clienteList;
    }


    @PutMapping("/{id}")
    public ResponseEntity<?> updateCliente (@RequestBody ClienteEntity clienteEntity, @PathVariable UUID id){

        var updateClient = this.clienteRepository.findById((id)).orElse(null);


        if(updateClient == null){
            return ResponseEntity.notFound().build();
        }

        Utils.copyNonNullProperties(clienteEntity, updateClient);

        var result = this.clienteRepository.save(updateClient);  

         return ResponseEntity.status(200).body(result);
        


    }


    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCliente (@PathVariable UUID id){

        if (!this.clienteRepository.existsById(id)) {
            return ResponseEntity.notFound().build(); 
        }

        this.clienteRepository.deleteById(id);
        return ResponseEntity.noContent().build(); 

        
    }


}
