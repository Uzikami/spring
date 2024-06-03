package com.uzikami.api.controller;

import com.uzikami.api.dto.HttpResponse;
import com.uzikami.api.entity.Buggy;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin("http://localhost:3000")
@RestController
@RequestMapping("/api/buggy")
public class BuggyController {
    @PostMapping("validate-error")
    public ResponseEntity<Buggy> createValidationError(@Valid @RequestBody Buggy buggy){
        System.out.println(buggy);
        return new ResponseEntity<>(buggy, HttpStatus.CREATED);
    }
    @GetMapping("500")
    public ResponseEntity<HttpResponse> createError500(){
        Buggy buggy = null;
        System.out.println(buggy.getName());
        return null;
    }
}
