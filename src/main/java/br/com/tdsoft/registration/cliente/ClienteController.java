package br.com.tdsoft.registration.cliente;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/cliente")
public class ClienteController {
    

    @Autowired
    private IClienteRepository clienteRepository;


    @PostMapping("/create")
    public ClienteEntity createCliente (@RequestBody ClienteEntity clienteEntity, HttpServletRequest request) {

        var idUser = request.getAttribute("idUser");

        clienteEntity.setIdUser((UUID) idUser);

        var resultCliente = this.clienteRepository.save(clienteEntity);

        return resultCliente;


    }
}
