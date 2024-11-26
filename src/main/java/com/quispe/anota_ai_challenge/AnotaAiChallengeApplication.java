package com.quispe.anota_ai_challenge;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
/*
 * Uma anotação central do Spring Boot que combina:
 * @Configuration: Indica que essa classe pode conter configurações do Spring.
 * @EnableAutoConfiguration: Habilita a configuração automática do Spring com base nas dependências presentes no projeto.
 * @ComponentScan: Faz o Spring escanear os pacotes em busca de componentes, serviços e configurações.
 */
@EnableMongoRepositories
/*
 * Essa anotação ativa o suporte do Spring Data para repositórios MongoDB.
 * Ela permite que você use interfaces que estendem MongoRepository,
 * para acessar e manipular dados no MongoDB de forma simples.
 */
public class AnotaAiChallengeApplication {

	public static void main(String[] args) {
		SpringApplication.run(AnotaAiChallengeApplication.class, args);
	}

}
