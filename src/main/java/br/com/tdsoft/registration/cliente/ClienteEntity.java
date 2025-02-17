package br.com.tdsoft.registration.cliente;

import java.time.LocalDateTime;
import java.util.UUID;
import org.hibernate.annotations.CreationTimestamp;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;
import jakarta.persistence.Column;


@Data
@Entity(name = "table_cliente" )
public class ClienteEntity {
    

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    private String name;

    @Column(length = 300)
    private String description;

    @CreationTimestamp
    private LocalDateTime createdAt;

    private UUID idUser;


    public void setDescription(String d) throws Exception{
        if(d.length() > 300){

            throw new Exception("Você passou do limite maximo de 300 caracteres");

        }
        this.description = d;
    }


}
