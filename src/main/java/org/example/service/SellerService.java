package org.example.service;

import org.example.Main;
import org.example.entity.Seller;
import org.example.exception.SellerDataException;
import org.example.repository.SellerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SellerService {
    SellerRepository sellerRepository;
    @Autowired
    public SellerService(SellerRepository sellerRepository){
        this.sellerRepository = sellerRepository;
    }
    public List<Seller> getAllSellers(){
        return sellerRepository.findAll();
    }

    public Seller saveSeller(Seller s) throws SellerDataException {
        if(s.getName().isEmpty()){ throw new SellerDataException("Seller name cannot be blank");}
        Main.log.info("Seller saved: "+ s);
        return sellerRepository.save(s);
    }
}
