package com.example.demo.dao;

import com.example.demo.entity.LoveRelation;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LoveRepository extends Neo4jRepository<LoveRelation,Long> {

}
