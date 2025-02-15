package br.com.tdsoft.registration.user;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

public interface IUserRepository extends JpaRepository<UserEntity, UUID> {
    
    UserEntity findByEmail (String email);
    
}
