package com.example.repository;

import com.example.domain.CityLog;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface LogRepository extends MongoRepository<CityLog, String> {
    public List<CityLog> findAllByName(String name);
}
