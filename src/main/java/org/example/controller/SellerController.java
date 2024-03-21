package org.example.controller;

import org.example.entity.Seller;
import org.example.exception.SellerDataException;
import org.example.exception.SellerNotFoundException;
import org.example.service.SellerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@RestController
public class SellerController {
    SellerService sellerService;
    public SellerController(SellerService sellerService){
        this.sellerService = sellerService;
    }
    @GetMapping("/seller")
    public ResponseEntity<List<Seller>> getAllSellers(){
        List<Seller> sellers = sellerService.getAllSellers();
        return new ResponseEntity<>(sellers, HttpStatus.OK);
    }

    @PostMapping("/seller")
    public ResponseEntity<?> addSeller(@RequestBody Seller s) {
        try{
            Seller seller = sellerService.saveSeller(s);
            return new ResponseEntity<>(seller, HttpStatus.CREATED);
        } catch (SellerDataException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }

    }
}
