package com.quispe.anota_ai_challenge.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.quispe.anota_ai_challenge.domain.category.exceptions.CategoryNotFoundException;
import com.quispe.anota_ai_challenge.domain.product.Product;
import com.quispe.anota_ai_challenge.domain.product.ProductDTO;
import com.quispe.anota_ai_challenge.domain.product.exceptions.ProductNotFoundException;
import com.quispe.anota_ai_challenge.repositories.ProductRepository;
import com.quispe.anota_ai_challenge.services.aws.AwsSnsService;
import com.quispe.anota_ai_challenge.services.aws.MessageDTO;

import lombok.RequiredArgsConstructor;

/*
 * @Service: Indica que esta classe é um serviço Spring. 
 * Serviços contêm a lógica de negócios do aplicativo e são usados pelos controladores.
 */
@Service
@RequiredArgsConstructor
public class ProductService {
   private final ProductRepository repository;
   private final CategoryService categoryService;
   private final AwsSnsService snsService;

    public List<Product> getAll(){
        return repository.findAll();
    }

    public Product get(String id){
        /*
         * orElseThrow(ProductNotFoundException::new): 
         * Lança a exceção personalizada ProductNotFoundException caso o produto não seja encontrado.
         */
        return repository.findById(id).orElseThrow(ProductNotFoundException::new);
    }
    
    public Product create(ProductDTO productDTO){
        Product newProduct = new Product(productDTO);
        categoryService.getById(productDTO.categoryId()).orElseThrow(CategoryNotFoundException::new);
        repository.save(newProduct);
        /*
         * Publica uma mensagem com os detalhes do novo produto na AWS SNS.
        */
        snsService.publish(new MessageDTO(newProduct.toString()));
        return newProduct;
    }
    
    public Product update(String id, ProductDTO productDTO){
        Product updateProduct = repository.findById(id).orElseThrow(ProductNotFoundException::new);
        
        if(!productDTO.categoryId().isEmpty()){
            categoryService.getById(productDTO.categoryId()).orElseThrow(ProductNotFoundException::new);
            updateProduct.setCategoryId(productDTO.categoryId());
        }
        if(!productDTO.title().isEmpty()) updateProduct.setTitle(productDTO.title());
        if(!productDTO.description().isEmpty()) updateProduct.setDescription(productDTO.description());
        if(productDTO.price() != null) updateProduct.setPrice(productDTO.price());
        /*
         * Publica uma mensagem com os detalhes do novo produto na AWS SNS.
        */
        snsService.publish(new MessageDTO(updateProduct.toString()));
        return updateProduct;
    }

    public void delete(String id){
        Product categoryToDelete = repository.findById(id).orElseThrow(ProductNotFoundException::new);
        snsService.publish(new MessageDTO(categoryToDelete.deleteToString()));
        repository.delete(categoryToDelete);
    }
}
