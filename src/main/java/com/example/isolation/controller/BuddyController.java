package com.example.isolation.controller;

import com.example.isolation.service.BuddyKafkaProducerService;
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
    private final BuddyKafkaProducerService buddyKafkaService;

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

    @PostMapping("/add-read-committed")
    public ResponseEntity<String> initAddLikeDefault(@RequestParam("name") String name, @RequestParam("like") int like) {
        try {
            buddyService.addLikeToBuddyDefault(name, like);
            return new ResponseEntity<>("Like successfully added.", HttpStatus.ACCEPTED);
        } catch (Exception e) {
            log.warn("ID: {} - Exception in controller:",Thread.currentThread().getId(), e);
            return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
        }
    }

    @PostMapping("/add-repeatable-read")
    public ResponseEntity<String> initAddLikeRepeatableRead(@RequestParam("name") String name, @RequestParam("like") int like) {
        try {
            buddyService.addLikeToBuddyRepeatableRead(name, like);
            return new ResponseEntity<>("Like successfully added.", HttpStatus.ACCEPTED);
        } catch (Exception e) {
            log.warn("ID: {} - Exception Repeatable Read:",Thread.currentThread().getId());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
        }
    }

    @PostMapping("/add-serializable")
    public ResponseEntity<String> initAddLikeSerializable(@RequestParam("name") String name, @RequestParam("like") int like) {
        try {
            buddyService.addLikeToBuddySerializable(name, like);
            return new ResponseEntity<>("Like successfully added.", HttpStatus.ACCEPTED);
        } catch (Exception e) {
            log.warn("ID: {} - Exception in controller:",Thread.currentThread().getId(), e);
            return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
        }
    }

    @PostMapping("/add-versioned")
    public ResponseEntity<String> initAddLikeVersioned(@RequestParam("name") String name, @RequestParam("like") int like) {
        try {
            buddyService.addLikeToBuddyVersioned(name, like);
            return new ResponseEntity<>("Like successfully added.", HttpStatus.ACCEPTED);
        } catch (Exception e) {
            log.warn("ID: {} - Exception in controller:",Thread.currentThread().getId(), e);
            return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
        }
    }

    @PostMapping("/add-pessimistic")
    public ResponseEntity<String> initAddLikePessimistic(@RequestParam("name") String name, @RequestParam("like") int like) {
        try {
            buddyService.addLikeToBuddyPessimistic(name, like);
            return new ResponseEntity<>("Like successfully added.", HttpStatus.ACCEPTED);
        } catch (Exception e) {
            log.warn("ID: {} - Exception in controller:",Thread.currentThread().getId(), e);
            return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
        }
    }

    @PostMapping("/add-over-kafka")
    public ResponseEntity<String> addLikeOverKafka(@RequestParam("name") String name, @RequestParam("like") int like) {
        try {
            buddyKafkaService.sendMessage(name, like);
            return new ResponseEntity<>("Like message sent.", HttpStatus.ACCEPTED);
        } catch (Exception e) {
            log.warn("ID: {} - Exception in controller:",Thread.currentThread().getId(), e);
            return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
        }
    }

    @PostMapping("/add-pessimistic-over-kafka")
    public ResponseEntity<String> initAddLikePessimisticOverKafka(@RequestParam("name") String name, @RequestParam("like") int like) {
        try {
            buddyService.addLikeToBuddyPessimistic(name, like);
            return new ResponseEntity<>("Like successfully added.", HttpStatus.ACCEPTED);
        } catch (Exception e) {
            log.warn("ID: {} - Exception in controller:",Thread.currentThread().getId(), e);
            try {
                buddyKafkaService.sendMessage(name, like);
                return new ResponseEntity<>("Like successfully added.", HttpStatus.ACCEPTED);
            } catch (Exception exception) {
                log.warn("ID: {} - Exception in controller:",Thread.currentThread().getId(), e);
                return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
            }
        }
    }
}
