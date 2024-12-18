package com.xavierbouclet.adventofcode;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api2")
public class TestController {

    @GetMapping("/hello")
    public String hello(){
        return "Hello Spring Doc";
    }


}
