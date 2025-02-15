package br.com.tdsoft.registration.cliente;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/cliente")
public class ClienteController {
    

    @Autowired
    private IClienteRepository clienteRepository;


    @PostMapping("/create")
    public ClienteEntity createCliente (@RequestBody ClienteEntity clienteEntity) {

        var resultCliente = this.clienteRepository.save(clienteEntity);

        return resultCliente;


    }
}
