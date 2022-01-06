package com.example.kafkaspring.producer;

import com.example.kafkaspring.People;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;


// nessa classe é informado o topico e a mensagem a ser transicionada
@Slf4j
@Component
public class PeopleProducer {

    private final String topicName;
    private final KafkaTemplate<String, People> kafkaTemplate; // o People utilizado é o que foi feito pelo Avro e não o do domain


    public PeopleProducer(@Value("${topic.name}") String topicName, KafkaTemplate<String, People> kafkaTemplate) {
        this.topicName = topicName;
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendMessage(People people){
        kafkaTemplate.send(topicName,(String) people.getId(), people).addCallback(
                success -> log.info("Mensagem Enviada com sucesso !"),
                failure -> log.error("Falha ao Enviar Mensagem !")
        );
    }
}
