package org.example.service;

import org.example.entity.Product;
import org.example.entity.Seller;
import org.example.exception.ProductDataException;
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
    public List<Product> getAllProducts() throws ProductNotFoundException {
        List<Product> productList = productRepository.findAll();
        if (productList.isEmpty()) {
            throw new ProductNotFoundException("There are no Products!");
        }
        return productList;
    }

    public List<Product> getProductByName(String name) throws ProductNotFoundException {
        List<Product> productToReturn = productRepository.findByName(name);
        if (productToReturn.isEmpty()) {
            throw new ProductNotFoundException("Product with name " + name + " does not exist");
        }
        return productRepository.findByName(name);
    }

    public Product getById(long id) throws ProductNotFoundException {
        Optional<Product> p = productRepository.findById(id);
        if(p.isEmpty()){
            throw new ProductNotFoundException("Product with id " + id + " does not exist");
        }else{
            return p.get();
        }
    }

    public Product saveProduct(long id, Product p) throws SellerNotFoundException, ProductDataException {
        Optional<Seller> optionalSeller = sellerRepository.findById(id);
        Seller s;
        if(optionalSeller.isEmpty()){
            throw new SellerNotFoundException("Seller not found");
        }else{
            s = optionalSeller.get();
        }
        if(p.getName().isEmpty()){ throw new ProductDataException("Product name cannot be blank");}
        if(p.getPrice() <= 0){ throw new ProductDataException("Product price cannot be $0 or negative");}

        Product savedProduct = productRepository.save(p);
        s.getProducts().add(savedProduct);
        sellerRepository.save(s);
        return savedProduct;
    }

    public Product updateProduct(long id, Product p) throws ProductNotFoundException, ProductDataException {
        Optional<Product> optionalProduct = productRepository.findById(id);
        Product productToUpdate;
        if(optionalProduct.isEmpty()){
            throw new ProductNotFoundException("Product with id " + id + " not found");
        }else{
            productToUpdate = optionalProduct.get();
        }
        if(p.getName().isEmpty()){ throw new ProductDataException("Product name cannot be blank");}
        if(p.getPrice() <= 0){ throw new ProductDataException("Product price cannot be $0 or negative");}

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
