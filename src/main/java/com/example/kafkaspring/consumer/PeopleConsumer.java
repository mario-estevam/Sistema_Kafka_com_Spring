package com.example.kafkaspring.consumer;


import com.example.kafkaspring.People;
import com.example.kafkaspring.domain.Movie;
import com.example.kafkaspring.repository.PeopleRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Slf4j
@Component
@AllArgsConstructor
public class PeopleConsumer {

    private final PeopleRepository peopleRepository;

    @KafkaListener(topics = "${topic.name}") // é passado o topico que foi definido no applicationl.properties
    public void consumer(ConsumerRecord<String, People> record, Acknowledgment ack){ // é necessário o tipo da String e o objeto que será consumido (o objeto people do avro), o ack é para não consumir uma mensagem que já foi lida
        var people = record.value(); // mensagem é recebida

        log.info("Mensagem consumida:" + people.toString());

        var peopleEntitiy = com.example.kafkaspring.domain.People.builder().build(); //obtendo instancia do objeto do domain
        peopleEntitiy.setId(people.getId().toString());
        peopleEntitiy.setCpf(people.getCpf().toString());
        peopleEntitiy.setName(people.getName().toString());
        peopleEntitiy.setMovies(people.getMovies().stream()
                .map(movie -> Movie.builder()  // para passar a lista de filmes, tive que utilizar o metodo stream para percorrer e obter os valores da minha lista de filmes
                        .people(peopleEntitiy)
                        .name(movie.toString())
                        .build()).collect(Collectors.toList()));

        peopleRepository.save(peopleEntitiy); // persistência
        ack.acknowledge();
    }
}
