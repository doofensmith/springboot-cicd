package com.softlaboratory.springbootcicd.controller;

import com.softlaboratory.springbootcicd.domain.dto.ProductDto;
import com.softlaboratory.springbootcicd.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api")
public class ProductController {

    @Autowired
    private ProductService service;

    @GetMapping(value = "/product")
    public ResponseEntity<Object> getAllProduct() {

        try {
            return service.getAllProduct();
        }catch (Exception e) {
            throw e;
        }

    }

    @GetMapping(value = "/product/{id}")
    public ResponseEntity<Object> getProductById(@PathVariable Long id) {

        try {
            return service.getProductById(id);
        }catch (Exception e) {
            throw e;
        }

    }

    @PostMapping(value = "/product")
    public ResponseEntity<Object> saveNewProduct(@RequestBody ProductDto productDto) {

        try {
            return service.saveNewProduct(productDto);
        }catch (Exception e) {
            throw e;
        }

    }

    @PatchMapping(value = "/product/{id}")
    public ResponseEntity<Object> updateProduct(@PathVariable Long id, @RequestBody ProductDto productDto) {
        try {
            return service.updateProduct(id, productDto);
        }catch (Exception e) {
            throw e;
        }

    }

    @DeleteMapping(value = "/product/{id}")
    public ResponseEntity<Object> deleteProduct(@PathVariable Long id) {

        try {
            return service.deleteProduct(id);
        }catch (Exception e) {
            throw e;
        }

    }

}
