package com.example.kafkaspring.repository;


import com.example.kafkaspring.domain.People;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PeopleRepository extends JpaRepository<People,String> {

}
