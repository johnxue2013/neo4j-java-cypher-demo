package com.example.demo.dao;

import com.example.demo.entity.PersonNode;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;

import java.util.Map;

@Repository
public interface PersonRepository extends Neo4jRepository<PersonNode,Long> {

    @Query("match p=(n)-[*]-(m) return p")
    Iterable<Map<String,Object>> findPath();
}
