package com.quispe.anota_ai_challenge.domain.category;

/*
 * record é uma nova funcionalidade do Java usada para representar dados imutáveis. 
 * Ele é uma alternativa para criar classes simples de dados (como DTOs) com menos código.
 */
public record CategoryDTO (String title, String description, String ownerId) {
    
}
