package org.example.service;

import org.example.Exception.ProductNotFoundException;
import org.example.entity.Product;
import org.example.entity.Seller;
import org.example.repository.ProductRepository;
import org.example.repository.SellerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {
    ProductRepository productRepository;
    SellerRepository sellerRepository;
    @Autowired
    public ProductService(ProductRepository productRepository){
        this.productRepository = productRepository;
        this.sellerRepository = sellerRepository;
    }
    public List<Product> getAllProducts(){
        return productRepository.findAll();
    }

    public List<Product> getAllProductsByName(String name) {
        return productRepository.findByName(name);
    }

    public Product getById(long id) throws ProductNotFoundException {
        Optional<Product> p = productRepository.findById(id);
        if(p.isEmpty()){
            throw new ProductNotFoundException("no such product exists");
        }else{
            return p.get();
        }
    }
}
