package com.quispe.anota_ai_challenge.domain.product;

import org.json.JSONObject;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Document(collection = "products")
@Getter
@Setter
@NoArgsConstructor
public class Product {
    /*
     * Indica que o campo id será a chave primária no MongoDB.
     */
    @Id
    private String id;
    private String title;
    private String description;
    private Integer price;
    private String ownerId;
    private String categoryId;

    public Product(ProductDTO productDTO) {
        this.title = productDTO.title();
        this.price = productDTO.price();
        this.ownerId = productDTO.ownerId();
        this.description = productDTO.description();
        this.categoryId = productDTO.categoryId();
    }

    @Override
    public String toString(){
        JSONObject json = new JSONObject();
        json.put("id", id);
        json.put("title", title);
        json.put("description", description);
        json.put("ownerId", ownerId);
        json.put("price", price);
        json.put("categoryId", categoryId);
        json.put("type", "product");

        return json.toString();
    }

    public String deleteToString(){
        JSONObject json = new JSONObject();
        json.put("id", this.id);
        json.put("ownerId", this.ownerId);
        json.put("type", "delete-product");

        return json.toString();
    }
}
