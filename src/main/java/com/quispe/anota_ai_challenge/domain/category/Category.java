package com.quispe.anota_ai_challenge.domain.category;

import org.json.JSONObject;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/*
 * Representa um coleção no MongoDB, é atrinuido um nome a coleção
 */
@Document(collection = "categories")
/*
 * Lombok: gera automaticamente métodos como getters, setters e construtores
 */
@Getter
@Setter
@NoArgsConstructor
public class Category {
    @Id
    private String id;
    private String title;
    private String description;
    private String ownerId;

    public Category(CategoryDTO categoryDTO){
        this.title = categoryDTO.title();
        this.description = categoryDTO.description();
        this.ownerId = categoryDTO.ownerId();
    }

    @Override
    public String toString(){
        JSONObject json = new JSONObject();
        json.put("id", id);
        json.put("title", title);
        json.put("description", description);
        json.put("ownerId", ownerId);
        json.put("type", "category");

        return json.toString();
    }

    public String deleteToString(){
        JSONObject json = new JSONObject();
        json.put("id", this.id);
        json.put("ownerId", this.ownerId);
        json.put("type", "delete-category");

        return json.toString();
    }
}
