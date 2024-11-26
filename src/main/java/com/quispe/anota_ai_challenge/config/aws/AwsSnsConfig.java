package com.quispe.anota_ai_challenge.config.aws;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.sns.AmazonSNS;
import com.amazonaws.services.sns.AmazonSNSAsyncClientBuilder;
import com.amazonaws.services.sns.model.Topic;

@Configuration
public class AwsSnsConfig {
    /*
     * ssas variáveis são preenchidas com valores definidos no arquivo de configuração da aplicação 
     * (geralmente application.properties ou application.yml).
     */
    @Value("${aws.accessKeyId}")
    private String accessKeyId;
    @Value("${aws.secretKey}")
    private String secretKey;
    @Value("${aws.region}")
    private String region;
    @Value("${aws.sns.topic.catalog.arn}")
    private String catalogTopicArn;

    /*
     * Define que o método retorna um bean gerenciado pelo Spring, 
     * disponível para injeção em outras partes do código.
     * O bean criado aqui representa o cliente da AWS SNS.
     */
    @Bean
    public AmazonSNS amazonSnsBuilder(){
        /*
         * Cria uma instância contendo as credenciais da AWS (accessKeyId e secretKey).
         */
        BasicAWSCredentials credentials = new BasicAWSCredentials(accessKeyId, secretKey);

        /*
         * É o construtor do cliente SNS.
         * withCredentials: Define as credenciais para autenticar na AWS.
         * withRegion: Especifica a região onde o serviço SNS será acessado.
         * build: Cria uma instância de AmazonSNS configurada.
         */
        return AmazonSNSAsyncClientBuilder
                .standard()
                .withCredentials(new AWSStaticCredentialsProvider(credentials))
                .withRegion(region)
                .build();
    }
    /*
     * Bean que representa o tópico SNS configurado, pronto para ser usado na aplicação.
     */
    @Bean(name="catalogEventsTopic")
    public Topic snsCatalogTopicBuilder(){
        return new Topic().withTopicArn(catalogTopicArn);
    }
}
