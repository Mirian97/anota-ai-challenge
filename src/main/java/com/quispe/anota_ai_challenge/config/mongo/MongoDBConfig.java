package com.quispe.anota_ai_challenge.config.mongo;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoClientDatabaseFactory;

/*
 * Anotação do Spring que indica que essa classe contém Configurações de Beans 
 * (objetos gerenciados pelo Spring).
 */
@Configuration
public class MongoDBConfig {
    /*
     * Interface para a criação de conexões com o banco de dados
     */
    public MongoDatabaseFactory mongoConfigure(){
        /*
         * Implementação para iniciar a conexão com o banco por uma string url
         */
        return new SimpleMongoClientDatabaseFactory("mongodb://localhost:27017/product-catalog");
    }
    /*
     * Mongo Template: Classe para 'interagir' com o banco de dados
     */
    @Bean
    public MongoTemplate mongoTemplate(){
        return new MongoTemplate(mongoConfigure());
    }
}
