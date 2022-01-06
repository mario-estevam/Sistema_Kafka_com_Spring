package com.example.kafkaspring.controller;

import lombok.Getter;

import java.util.List;
@Getter
public class PeopleDTO { // data transform object
    private String name;
    private String cpf;

    private List<String> movies;
}
