package com.quispe.anota_ai_challenge.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.quispe.anota_ai_challenge.domain.product.Product;

/*
 * Marca essa interface como um repositório do Spring.
 * Embora opcional (pois o Spring Data já reconhece automaticamente interfaces que estendem MongoRepository como repositórios), 
 * é boa prática incluí-la para melhorar a clareza do código.
 */
@Repository
public interface ProductRepository extends MongoRepository<Product, String> {

    
}