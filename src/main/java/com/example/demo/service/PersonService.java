package com.example.demo.service;

import com.example.demo.entity.LoveRelation;
import com.example.demo.entity.PersonNode;
import org.neo4j.ogm.model.Result;

import java.util.List;

/**
 * @author han.xue
 * @since 2021-08-09 17:48:48
 */
public interface PersonService {
    PersonNode addPerson(PersonNode person);
    PersonNode findOnePerson(long id);
    LoveRelation loves(LoveRelation love);
    List<LoveRelation> findAllById(long id);

    Result searchPath();
}
