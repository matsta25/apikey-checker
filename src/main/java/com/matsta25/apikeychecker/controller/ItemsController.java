package com.matsta25.apikeychecker.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RestController
public class ItemsController {

    @GetMapping("/api/items")
    public List<String> getItems() {
        return Arrays.asList("Protected item 1.", "Protected item 2.", "Protected item 3.");
    }
}
