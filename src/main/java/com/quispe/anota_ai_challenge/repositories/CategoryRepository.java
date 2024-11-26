package com.quispe.anota_ai_challenge.repositories;


import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.quispe.anota_ai_challenge.domain.category.Category;

@Repository
public interface CategoryRepository extends MongoRepository<Category, String> {

}