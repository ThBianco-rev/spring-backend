package org.example.service;

import org.example.Main;
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
            Main.log.warn("Product GET attempted but there are not products");
            throw new ProductNotFoundException("There are no Products!");
        }
        Main.log.info("Product list returned: " + productList);
        return productList;
    }

    public List<Product> getProductByName(String name) throws ProductNotFoundException {
        List<Product> productToReturn = productRepository.findByName(name);
        if (productToReturn.isEmpty()) {
            Main.log.warn("Product with name " + name + " does not exist");
            throw new ProductNotFoundException("Product with name " + name + " does not exist");
        }
        Main.log.info("Product returned: " + productToReturn);
        return productToReturn;
    }

    public Product getById(long id) throws ProductNotFoundException {
        Optional<Product> p = productRepository.findById(id);
        if(p.isEmpty()){
            Main.log.warn("Product with id " + id + " does not exist");
            throw new ProductNotFoundException("Product with id " + id + " does not exist");
        }else{
            Main.log.info("Product returned: " + p.get());
            return p.get();
        }
    }

    public Product saveProduct(long id, Product p) throws SellerNotFoundException, ProductDataException {
        Optional<Seller> optionalSeller = sellerRepository.findById(id);
        Seller s;
        if(optionalSeller.isEmpty()){
            Main.log.warn("Seller with id " + id + " not found");
            throw new SellerNotFoundException("Seller with id " + id + " not found");
        }else{
            s = optionalSeller.get();
        }
        if(p.getName().isEmpty()){
            Main.log.warn("Product name cannot be blank");
            throw new ProductDataException("Product name cannot be blank");}
        if(p.getPrice() <= 0){
            Main.log.warn("Product price cannot be $0 or negative");
            throw new ProductDataException("Product price cannot be $0 or negative");}
        if(isDuplicateProductName(p)) {
            Main.log.warn("Product name is duplicative");
            throw new ProductDataException("Product name cannot be duplicate to existing product");}
        p.setSeller(s);
        Product savedProduct = productRepository.save(p);
        s.getProducts().add(savedProduct);
        sellerRepository.save(s);
        Main.log.info("Product added: " + savedProduct);
        return savedProduct;
    }

    public Product updateProduct(long id, Product p) throws ProductNotFoundException, ProductDataException {
        Optional<Product> optionalProduct = productRepository.findById(id);
        Product productToUpdate;
        if(optionalProduct.isEmpty()){
            Main.log.warn("Product with id " + id + " not found");
            throw new ProductNotFoundException("Product with id " + id + " not found");
        }else{
            productToUpdate = optionalProduct.get();
        }
        if(p.getName().isEmpty()){
            Main.log.warn("Product name cannot be blank");
            throw new ProductDataException("Product name cannot be blank");}
        if(p.getPrice() <= 0){
            Main.log.warn("Product price cannot be $0 or negative");
            throw new ProductDataException("Product price cannot be $0 or negative");}

        Main.log.info("Product updated - old product: " + productToUpdate);
        // Set name and price of product
        productToUpdate.setName(p.getName());
        productToUpdate.setPrice(p.getPrice());
        Main.log.info("Product updated - new product: " + productToUpdate);
        return productRepository.save(productToUpdate);
    }

    public Product deleteProduct(long id) throws ProductNotFoundException {
        Optional<Product> optionalProduct = productRepository.findById(id);
        Product productToDelete;
        if(optionalProduct.isEmpty()){
            Main.log.warn("Product with id " + id + " not found");
            throw new ProductNotFoundException("Product with id " + id + " not found");
        }else{
            productToDelete = optionalProduct.get();
        }
        productRepository.delete(productToDelete);
        Main.log.info("Product deleted: " + productToDelete);
        return productToDelete;
    }

    public boolean isDuplicateProductName(Product p){
        return(!productRepository.findByName(p.getName()).isEmpty());
    }
}
