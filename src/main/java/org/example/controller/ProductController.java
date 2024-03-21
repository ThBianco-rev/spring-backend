package org.example.controller;

import org.example.entity.Product;
import org.example.exception.ProductDataException;
import org.example.exception.ProductNotFoundException;
import org.example.exception.SellerNotFoundException;
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
    public ResponseEntity<?> getProductByName(@RequestParam String name){
        List<Product> product;
        if (name == null) {
            try{
                product = productService.getAllProducts();
                return new ResponseEntity<>(product, HttpStatus.OK);
            }catch(ProductNotFoundException e){
                return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
            }
        }else{
            try{
                product = productService.getProductByName(name);
            }catch(ProductNotFoundException e){
                return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
            }
        }
        return new ResponseEntity<>(product, HttpStatus.OK);
    }

    @GetMapping(value = "product")
    public ResponseEntity<?> getAllProducts(){
        try{
            List<Product> product;
            product = productService.getAllProducts();
            return new ResponseEntity<>(product, HttpStatus.OK);
        }catch(ProductNotFoundException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("product/{id}")
    public ResponseEntity<?> getById(@PathVariable long id){
        try{
            Product p = productService.getById(id);
            return new ResponseEntity<>(p, HttpStatus.OK);
        }catch (ProductNotFoundException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/seller/{id}/product")
    public ResponseEntity<?> addProduct(@RequestBody Product p, @PathVariable long id) {
        try{
            Product product = productService.saveProduct(id, p);
            return new ResponseEntity<>(product, HttpStatus.CREATED);
        }catch (SellerNotFoundException | ProductDataException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("product/{id}")
    public ResponseEntity<?> updateProduct(@RequestBody Product p, @PathVariable long id){
        try{
            Product product = productService.updateProduct(id, p);
            return new ResponseEntity<>(product, HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("product/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable long id) {
        try {
            Product product = productService.deleteProduct(id);
            return new ResponseEntity<>(product, HttpStatus.OK);
        } catch (ProductNotFoundException e) {
            // Even if product is not found, return 200 status - this is convention
            return new ResponseEntity<>(e.getMessage(), HttpStatus.OK);
        }
    }
}
