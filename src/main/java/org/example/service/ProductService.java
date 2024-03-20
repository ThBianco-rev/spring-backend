package org.example.service;

import org.example.entity.Product;
import org.example.entity.Seller;
import org.example.exception.ProductNotFoundException;
import org.example.exception.SellerNotFoundException;
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
    public ProductService(ProductRepository productRepository, SellerRepository sellerRepository){
        this.productRepository = productRepository;
        this.sellerRepository = sellerRepository;
    }
    public List<Product> getAllProducts(){
        return productRepository.findAll();
    }

    public List<Product> getProductByName(String name) {
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

    public Product saveProduct(long id, Product p) throws SellerNotFoundException {
        Optional<Seller> optionalSeller = sellerRepository.findById(id);
        Seller s;
        if(optionalSeller.isEmpty()){
            throw new SellerNotFoundException("Seller not found");
        }else{
            s = optionalSeller.get();
        }
        Product savedProduct = productRepository.save(p);
        s.getProducts().add(savedProduct);
        sellerRepository.save(s);
        return savedProduct;
    }
    public Product updateProduct(long id, Product p) throws ProductNotFoundException {
        Optional<Product> optionalProduct = productRepository.findById(id);
        Product productToUpdate;
        if(optionalProduct.isEmpty()){
            throw new ProductNotFoundException("Product with id " + id + " not found");
        }else{
            productToUpdate = optionalProduct.get();
        }
        // Set name and price of product
        productToUpdate.setName(p.getName());
        productToUpdate.setPrice(p.getPrice());
        return productRepository.save(productToUpdate);
    }

    public Product deleteProduct(long id) throws ProductNotFoundException {
        Optional<Product> optionalProduct = productRepository.findById(id);
        Product productToDelete;
        if(optionalProduct.isEmpty()){
            throw new ProductNotFoundException("Product with id " + id + " not found");
        }else{
            productToDelete = optionalProduct.get();
        }
        productRepository.delete(productToDelete);
        return productToDelete;
    }
}
