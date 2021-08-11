package com.example.demo.dao;

import com.example.demo.entity.LoveRelation;
import com.example.demo.entity.PersonNode;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface PersonRepository extends Neo4jRepository<PersonNode,Long> {

    //返回节点n以及n指向的所有节点与关系
    @Query("MATCH p=(n:Person)-[r:LOVES]->(m:Person) WHERE id(n)={id} RETURN p")
    List<LoveRelation> findAllById(@Param("id") long id);


    @Query("match p=(n)-[*]-(m) return p")
    Iterable<Map<String,Object>> findPath();
}
