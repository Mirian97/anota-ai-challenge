package com.quispe.anota_ai_challenge.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.quispe.anota_ai_challenge.domain.category.Category;
import com.quispe.anota_ai_challenge.domain.category.CategoryDTO;
import com.quispe.anota_ai_challenge.domain.category.exceptions.CategoryNotFoundException;
import com.quispe.anota_ai_challenge.repositories.CategoryRepository;
import com.quispe.anota_ai_challenge.services.aws.AwsSnsService;
import com.quispe.anota_ai_challenge.services.aws.MessageDTO;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CategoryService {
    private final CategoryRepository repository;
    private final AwsSnsService snsService;

    public List<Category> getAll(){
        return repository.findAll();
    }

    public Optional<Category> getById(String id){
        return repository.findById(id);
    }
    
    public Category create(CategoryDTO categoryDTO){
        Category newCategory = new Category(categoryDTO);
        repository.save(newCategory);
        snsService.publish(new MessageDTO(newCategory.toString()));
        return newCategory;
    }
    
    public Category update(String id, CategoryDTO categoryDTO){
        Category updatedCategory = repository.findById(id).orElseThrow(CategoryNotFoundException::new);
        
        if(!categoryDTO.title().isEmpty()) updatedCategory.setTitle(categoryDTO.title());
        if(!categoryDTO.description().isEmpty()) updatedCategory.setDescription(categoryDTO.description());
        snsService.publish(new MessageDTO(updatedCategory.toString()));
        
        return updatedCategory;
    }

    public void delete(String id){
        Category categoryToDelete = repository.findById(id).orElseThrow(CategoryNotFoundException::new);
        snsService.publish(new MessageDTO(categoryToDelete.deleteToString()));
        repository.delete(categoryToDelete);
    }
}
