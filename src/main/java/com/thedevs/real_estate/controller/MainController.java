package com.thedevs.real_estate.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.atomic.AtomicLong;

@RestController
public class MainController {
    private static final String template = "Hello, ";

    @RequestMapping("/")
    String home(@RequestParam(defaultValue = "World") String name) {
        return template + name + '!';
    }
}
