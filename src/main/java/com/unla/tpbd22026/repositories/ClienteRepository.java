package com.unla.tpbd22026.repositories;

import com.unla.tpbd22026.models.Cliente;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ClienteRepository extends MongoRepository<Cliente, String> {
}
