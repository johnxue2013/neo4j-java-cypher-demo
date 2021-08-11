package com.example.demo.controller;

import com.example.demo.entity.LoveRelation;
import com.example.demo.entity.PersonNode;
import com.example.demo.service.PersonService;
import org.neo4j.ogm.model.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author han.xue
 * @since 2021-08-09 17:51:51
 */
@RestController
@RequestMapping("/person")
public class PersonController {

    @Autowired
    private PersonService personService;

    @RequestMapping("/addPerson/{name}")
    public PersonNode addPerson(@PathVariable("name") String name) {
        PersonNode person = new PersonNode();
        person.setName(name);
        return personService.addPerson(person);
    }

    @RequestMapping("/loves/{id1}/{id2}")
    public LoveRelation loves(@PathVariable("id1") String id1, @PathVariable("id2") String id2) {
        PersonNode person1 = personService.findOnePerson(Long.parseLong(id1));
        PersonNode person2 = personService.findOnePerson(Long.parseLong(id2));
        LoveRelation love = new LoveRelation();
        love.setStartNode(person1);
        love.setEndNode(person2);
        return personService.loves(love);
    }


    @RequestMapping("/findAllById/{id}")
    List<LoveRelation> findAllById(@PathVariable("id") Long id) {
        return personService.findAllById(id);
    }

    @RequestMapping("/path")
    Result path() {
        Result result = personService.searchPath();
        System.out.println(result);
        return result;
    }
}
