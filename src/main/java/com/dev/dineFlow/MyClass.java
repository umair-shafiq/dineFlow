package com.dev.dineFlow;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/abc")
public class MyClass
{
    @GetMapping
    public String abc(){
        return "Umair Sultani";
    }
}
