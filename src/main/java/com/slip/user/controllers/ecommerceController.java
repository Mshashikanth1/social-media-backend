package com.slip.user.controllers;

import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/api/v1/ecom")
public class ecommerceController {
    @GetMapping
    public void getAllProducts(){
    }

    @GetMapping("/{pid}")
    public void getProduct(@PathVariable String pid){
    }

    @PostMapping("/sell")
    public void sellProduct(){
    }

    @PostMapping("/buy")
    public void buyProduct(){
    }
}
