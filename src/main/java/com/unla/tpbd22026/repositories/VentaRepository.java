package com.unla.tpbd22026.repositories;

import com.unla.tpbd22026.models.Venta;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VentaRepository extends MongoRepository<Venta, String> {
}
