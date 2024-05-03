package org.africalib.forms.common.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MainController {

    @GetMapping("/api/greeting")
    public String greeting() {
        return "Hello";
    }
}
