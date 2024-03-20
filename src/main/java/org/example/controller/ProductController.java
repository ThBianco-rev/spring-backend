package org.example.controller;

import org.example.entity.Product;
import org.example.exception.ProductNotFoundException;
import org.example.service.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ProductController {
        ProductService productService;
        public ProductController(ProductService productService) {
            this.productService = productService;
        }
    @GetMapping(value="/product", params = "name")
    public ResponseEntity<List<Product>> getProductByName(@RequestParam String name){
        List<Product> product;
        if (name == null) {
            product = productService.getAllProducts();
        }else{
            product = productService.getProductByName(name);
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

    @PostMapping("/seller/{id}/product")
    public ResponseEntity<Product> addProduct(@RequestBody Product p, @PathVariable long id) {
        try{
            Product product = productService.saveProduct(id, p);
            return new ResponseEntity<>(product, HttpStatus.CREATED);
        }catch (Exception e){
            System.out.println(e);
        }
        return null;
    }

    @PutMapping("product/{id}")
    public ResponseEntity<Product> updateProduct(@RequestBody Product p, @PathVariable long id){
        try{
            Product product = productService.updateProduct(id, p);
            return new ResponseEntity<>(product, HttpStatus.OK);
        }catch (Exception e){
            System.out.println(e);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("product/{id}")
    public ResponseEntity<Product> deleteProduct(@PathVariable long id) {
        try {
            Product product = productService.deleteProduct(id);
            return new ResponseEntity<>(product, HttpStatus.OK);
        } catch (Exception e) {
            System.out.println(e);
            // Even if product is not found, return 200 status - this is convention
            return new ResponseEntity<>(HttpStatus.OK);
        }
    }
}
