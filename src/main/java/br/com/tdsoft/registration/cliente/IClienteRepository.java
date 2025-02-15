package br.com.tdsoft.registration.cliente;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

public interface IClienteRepository extends JpaRepository<ClienteEntity, UUID> {

    

    
}
