package com.example.kafkaspring.controller;

import com.example.kafkaspring.People;
import com.example.kafkaspring.producer.PeopleProducer;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.UUID;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/peopless")

@AllArgsConstructor
public class PeopleController {

    private final PeopleProducer peopleProducer; //injeção de dependencias do produtor

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> sendMessage(@RequestBody PeopleDTO peopleDTO){ // metodo que envia mensagem, e recebe como argumento o DTO
        var id = UUID.randomUUID().toString();
        var message = People.newBuilder() // People criado pelo avro
                .setId(id)
                .setName(peopleDTO.getName())
                .setCpf(peopleDTO.getCpf())
                .setMovies(peopleDTO.getMovies().stream().map(p -> (CharSequence) p).collect(Collectors.toList()))
                .build();

        peopleProducer.sendMessage(message);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
