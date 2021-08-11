package com.example.demo.service.impl;

import com.example.demo.dao.LoveRepository;
import com.example.demo.dao.PersonRepository;
import com.example.demo.entity.LoveRelation;
import com.example.demo.entity.PersonNode;
import com.example.demo.service.PersonService;
import org.neo4j.driver.v1.types.Path;
import org.neo4j.ogm.model.Result;
import org.neo4j.ogm.session.Session;
import org.neo4j.ogm.session.SessionFactory;
import org.neo4j.ogm.transaction.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author han.xue
 * @since 2021-08-09 17:49:49
 */
@Service
public class PersonServiceImpl implements PersonService {

    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private LoveRepository loveRepository;

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public PersonNode addPerson(PersonNode person){
        return personRepository.save(person);
    }

    @Override
    public PersonNode findOnePerson(long id) {
        return personRepository.findById(id).get();
    }

    @Override
    public LoveRelation loves(LoveRelation love) {
        return loveRepository.save(love);
    }

    @Override
    public List<LoveRelation> findAllById(long id) {
        return personRepository.findAllById(id);
    }

    @Override
    public Result searchPath() {
//        String cypher = "MATCH p=(n:Person {name:'zhangsan'})-[r]->(m) RETURN p";
//        String cypher = "MATCH p=(n:Person {name:'zhangsan'})-[r]->(m)  RETURN nodes(p) as people,relationships(p)";
//        Session session = sessionFactory.openSession();
//        Result result = session.query(cypher, new HashMap<>());
//
//        Iterable<Map<String, Object>> maps = result.queryResults();
//
//        System.out.println(maps);
//
//        Object forObject = session.query(Object.class, cypher, new HashMap<>());

        Iterable<Map<String, Object>> itrt = personRepository.findPath();
        System.out.println(itrt);


        List<List<Integer>> result = new ArrayList<>();
        for(Map<String,Object> row : itrt){

            Map<String, Object> path = (Map<String, Object>) row.get("p");
            List<String> rls = (List<String>) path.get("relationships");
            List<String> nodes = (List<String>) path.get("nodes");
            List<String> directions = (List<String>) path.get("directions");
            String start = (String) path.get("start");
            String end = (String) path.get("end");
            Long length = (Long) path.get("length");

            System.out.println(rls);
            System.out.println(nodes);
            System.out.println(directions);
            System.out.println(start);
            System.out.println(end);
            System.out.println(length);

            System.out.println("---------------------");


        }

        System.out.println(result);


        return null;
    }

}

