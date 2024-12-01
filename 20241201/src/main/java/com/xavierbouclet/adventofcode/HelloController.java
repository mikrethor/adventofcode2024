package com.xavierbouclet.adventofcode;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class HelloController {

    @GetMapping("/hello/{someone}")
    public String hello(@PathVariable("someone") String variable){
        return "Hello %s!".formatted(variable);
    }

    @GetMapping("/hello")
    public String helloworld(){
        return "Hello world!";
    }
}
