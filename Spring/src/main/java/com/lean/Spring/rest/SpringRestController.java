package com.lean.Spring.rest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SpringRestController
{
    
    @GetMapping("/rest")
    public String get(){
        return "Hello Rest";
    }
}
