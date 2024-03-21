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
        if(s.getName().isEmpty()){
            Main.log.warn("Seller name empty: "+ s);
            throw new SellerDataException("Seller name cannot be blank");}
        Seller savedSeller = sellerRepository.save(s);
        Main.log.info("Seller saved: "+ savedSeller);
        return savedSeller;
    }
}
