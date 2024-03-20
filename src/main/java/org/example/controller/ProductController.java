package org.example.controller;

import org.example.Exception.ProductNotFoundException;
import org.example.entity.Product;
import org.example.service.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ProductController {
        ProductService productService;
        public ProductController(ProductService productService) {
            this.productService = productService;
        }
    @GetMapping(value="/product", params = "name")
    public ResponseEntity<List<Product>> getAllProductsByName(@RequestParam String name){
        List<Product> product;
        if (name == null) {
            product = productService.getAllProducts();
        }else{
            product = productService.getAllProductsByName(name);
        }
        return new ResponseEntity<>(product, HttpStatus.OK);
    }

    @GetMapping(value = "product")
    public ResponseEntity<List<Product>> getAllProducts(){
        List<Product> product;
        product = productService.getAllProducts();
        return new ResponseEntity<>(product, HttpStatus.OK);
    }

    @GetMapping("product/{id}")
    public ResponseEntity<Product> getById(@PathVariable long id){
        try{
            Product p = productService.getById(id);
            return new ResponseEntity<>(p, HttpStatus.OK);
        }catch (ProductNotFoundException e){
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }


}
