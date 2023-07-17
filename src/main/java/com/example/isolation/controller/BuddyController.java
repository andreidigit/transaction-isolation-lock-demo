package com.example.isolation.controller;

import com.example.isolation.service.BuddyService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Log4j2
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class BuddyController {
    private final BuddyService buddyService;

    @PostMapping("/add")
    public ResponseEntity<String> initAddLike(@RequestParam("name") String name, @RequestParam("like") int like) {
        try {
            buddyService.addLikeToBuddy(name, like);
            return new ResponseEntity<>("Like successfully added.", HttpStatus.ACCEPTED);
        } catch (Exception e) {
            log.warn("ID: {} - Exception in controller:",Thread.currentThread().getId(), e);
            return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
        }
    }
}
