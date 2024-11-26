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

import com.quispe.anota_ai_challenge.domain.category.Category;
import com.quispe.anota_ai_challenge.domain.category.CategoryDTO;
import com.quispe.anota_ai_challenge.domain.category.exceptions.CategoryNotFoundException;
import com.quispe.anota_ai_challenge.services.CategoryService;

import lombok.RequiredArgsConstructor;
/*
 * @RestController: Indica que esta classe é um controlador do Spring. 
 * Ele lida com requisições HTTP e retorna respostas JSON automaticamente.
 * @RequestMapping("/api/category"): Define a rota base para este controlador
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/category")
public class CategoryController {
    private final CategoryService service;
    
    @GetMapping("/{id}")
    public ResponseEntity<Category> get(@PathVariable("id") String id){
        Category category = this.service.getById(id).orElseThrow(CategoryNotFoundException::new);
        return ResponseEntity.ok().body(category);  
    }

    @GetMapping
    public ResponseEntity<List<Category>> getAll(){
        List<Category> categories = this.service.getAll();
        return ResponseEntity.ok().body(categories);  
    }
    
    @PostMapping
    public ResponseEntity<Category> create(@RequestBody CategoryDTO category){
        Category newCategory = this.service.create(category);
        return ResponseEntity.ok().body(newCategory);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Category> update(@PathVariable("id") String id, @RequestBody CategoryDTO category){
        Category updatedCategory = this.service.update(id, category);
        return ResponseEntity.ok().body(updatedCategory);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable("id") String id){
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
