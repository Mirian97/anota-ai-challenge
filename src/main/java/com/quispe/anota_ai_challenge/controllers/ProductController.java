package com.quispe.anota_ai_challenge.controllers;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.quispe.anota_ai_challenge.domain.product.Product;
import com.quispe.anota_ai_challenge.domain.product.ProductDTO;
import com.quispe.anota_ai_challenge.services.ProductService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/product")
public class ProductController {
    private final ProductService service;

    @GetMapping("/{id}")
    public ResponseEntity<Product> get(@PathVariable("id") String id){
        Product product = service.get(id);
        return ResponseEntity.ok().body(product);  
    }
    
    @GetMapping
    public ResponseEntity<List<Product>> getAll(){
        List<Product> products = service.getAll();
        return ResponseEntity.ok().body(products);  
    }
    
    @PostMapping
    public ResponseEntity<Product> create(@RequestBody ProductDTO product){
        Product newProduct = service.create(product);
        return ResponseEntity.ok().body(newProduct);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Product> update(@PathVariable("id") String id, @RequestBody ProductDTO product){
        Product updatedCategory = service.update(id, product);
        return ResponseEntity.ok().body(updatedCategory);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable("id") String id){
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
